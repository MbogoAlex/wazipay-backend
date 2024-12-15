package com.escrow.wazipay.purchase.service.seller;

import com.escrow.wazipay.business.dao.BusinessDao;
import com.escrow.wazipay.business.entity.Business;
import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.purchase.dao.EscrowTransactionDao;
import com.escrow.wazipay.purchase.dto.CreateEscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.EscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.UpdateEscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.mapper.EscrowTransactionDtoMapper;
import com.escrow.wazipay.purchase.entity.EscrowTransaction;
import com.escrow.wazipay.purchase.entity.ProductStatus;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class SellerServiceImpl implements SellerService{
    private final EscrowTransactionDtoMapper escrowTransactionDtoMapper = new EscrowTransactionDtoMapper();
    private final EscrowTransactionDao escrowTransactionDao;
    private final UserDao userDao;
    private final SettingsDao settingsDao;
    private final BusinessDao businessDao;

    private static final int PIN_LENGTH = 4;
    @Autowired
    public SellerServiceImpl(
            EscrowTransactionDao escrowTransactionDao,
            UserDao userDao,
            SettingsDao settingsDao,
            BusinessDao businessDao
    ) {
        this.escrowTransactionDao = escrowTransactionDao;
        this.userDao = userDao;
        this.settingsDao = settingsDao;
        this.businessDao = businessDao;
    }
    @Transactional
    @Override
    public EscrowTransactionDto createEscrowTransaction(CreateEscrowTransactionDto createEscrowTransactionDto, Integer sellerId) {
        UserAccount seller = userDao.getUserByUserId(sellerId);
        String purchaseCode = generateUniquePurchaseCode();

        Business business = businessDao.getBusinessById(createEscrowTransactionDto.getBusinessId());


        EscrowTransaction escrowTransaction = EscrowTransaction.builder()
                .purchaseCode(purchaseCode)
                .productName(createEscrowTransactionDto.getProductName())
                .productDescription(createEscrowTransactionDto.getProductDescription())
                .amount(createEscrowTransactionDto.getAmount())
                .seller(seller)
                .business(business)
                .buyer(null)
                .paid(false)
                .status(ProductStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        EscrowTransaction savedTransaction = escrowTransactionDao.createEscrowTransaction(escrowTransaction);
        escrowTransaction.setPaymentLink(settingsDao.findBySettingsKey("domain").getValue() + "/api/payment/details/"+savedTransaction.getId());

        return escrowTransactionDtoMapper.toEscrowTransactionDto(escrowTransactionDao.updateEscrowTransaction(escrowTransaction));
    }
    @Transactional
    @Override
    public EscrowTransactionDto sellerUpdateEscrowTransaction(UpdateEscrowTransactionDto updateEscrowTransactionDto) {
        EscrowTransaction escrowTransaction = escrowTransactionDao.getEscrowTransaction(updateEscrowTransactionDto.getId());

        if(!updateEscrowTransactionDto.getProductName().equals(escrowTransaction.getProductName())) {
            escrowTransaction.setProductName(updateEscrowTransactionDto.getProductName());
            escrowTransaction.setUpdatedAt(LocalDateTime.now());
        }

        if(!updateEscrowTransactionDto.getProductDescription().equals(escrowTransaction.getProductDescription())) {
            escrowTransaction.setProductDescription(updateEscrowTransactionDto.getProductDescription());
            escrowTransaction.setUpdatedAt(LocalDateTime.now());
        }

        if(!updateEscrowTransactionDto.getAmount().equals(escrowTransaction.getAmount())) {
            escrowTransaction.setAmount(updateEscrowTransactionDto.getAmount());
            escrowTransaction.setUpdatedAt(LocalDateTime.now());
        }

        return escrowTransactionDtoMapper.toEscrowTransactionDto(escrowTransactionDao.updateEscrowTransaction(escrowTransaction));
    }

    public String generateUniquePurchaseCode() {
        String pin;
        do {
            pin = generateRandomPin();
        } while (escrowTransactionDao.existsByPurchaseCode(pin));
        return pin;
    }

    private String generateRandomPin() {
        return String.format("%0" + SellerServiceImpl.PIN_LENGTH + "d", ThreadLocalRandom.current().nextInt((int) Math.pow(10, SellerServiceImpl.PIN_LENGTH)));
    }
}
