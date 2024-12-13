package com.escrow.wazipay.purchase.service.delivery;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;
import com.escrow.wazipay.delivery.entity.DeliveryStatus;
import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.purchase.dao.DeliveryDao;
import com.escrow.wazipay.purchase.dao.EscrowTransactionDao;
import com.escrow.wazipay.purchase.dto.CreateDeliveryAssignmentDto;
import com.escrow.wazipay.purchase.dto.DeliveryAssignmentDto;
import com.escrow.wazipay.purchase.dto.UpdateDeliveryAssignmentDto;
import com.escrow.wazipay.purchase.dto.mapper.DeliveryAssignmentDtoMapper;
import com.escrow.wazipay.purchase.dto.mapper.EscrowTransactionDtoMapper;
import com.escrow.wazipay.purchase.entity.EscrowTransaction;
import com.escrow.wazipay.purchase.entity.ProductStatus;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.userWallet.dao.UserWalletDao;
import com.escrow.wazipay.userWallet.entity.UserWalletTransaction;
import com.escrow.wazipay.userWallet.entity.UserWalletTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
public class DeliveryServiceImpl implements DeliveryService{
    private final EscrowTransactionDtoMapper escrowTransactionDtoMapper = new EscrowTransactionDtoMapper();
    private final DeliveryAssignmentDtoMapper deliveryAssignmentDtoMapper = new DeliveryAssignmentDtoMapper();
    private final DeliveryDao deliveryDao;
    private final EscrowTransactionDao escrowTransactionDao;
    private final UserDao userDao;
    private final SettingsDao settingsDao;
    private final UserWalletDao userWalletDao;
    @Autowired
    public DeliveryServiceImpl(
            EscrowTransactionDao escrowTransactionDao,
            UserDao userDao,
            SettingsDao settingsDao,
            DeliveryDao deliveryDao,
            UserWalletDao userWalletDao
    ) {
        this.escrowTransactionDao = escrowTransactionDao;
        this.userDao = userDao;
        this.settingsDao = settingsDao;
        this.deliveryDao = deliveryDao;
        this.userWalletDao = userWalletDao;
    }
    @Transactional
    @Override
    public DeliveryAssignmentDto createDeliveryAssignment(CreateDeliveryAssignmentDto createDeliveryAssignmentDto) {
        UserAccount userAccount = userDao.getUserByUserId(createDeliveryAssignmentDto.getDeliveryManId());
        EscrowTransaction escrowTransaction = escrowTransactionDao.getEscrowTransaction(createDeliveryAssignmentDto.getTransactionId());

        DeliveryAssignment deliveryAssignment = DeliveryAssignment.builder()
                .transaction(escrowTransaction)
                .deliveryMan(userAccount)
                .status(DeliveryStatus.IN_TRANSIT)
                .createdAt(LocalDateTime.now())
                .deliveryLocation(createDeliveryAssignmentDto.getDeliveryLocation())
                .build();

        return deliveryAssignmentDtoMapper.toDeliveryAssignmentDto(deliveryDao.createDeliveryAssignment(deliveryAssignment));
    }
    @Transactional
    @Override
    public DeliveryAssignmentDto updateDeliveryAssignment(UpdateDeliveryAssignmentDto updateDeliveryAssignmentDto) {
        DeliveryAssignment deliveryAssignment = deliveryDao.getDeliveryAssignment(updateDeliveryAssignmentDto.getDeliveryId());
        UserAccount deliveryMan = userDao.getUserByUserId(updateDeliveryAssignmentDto.getDeliveryManId());

        if(!updateDeliveryAssignmentDto.getDeliveryLocation().equals(deliveryAssignment.getDeliveryLocation())) {
            deliveryAssignment.setDeliveryLocation(updateDeliveryAssignmentDto.getDeliveryLocation());
            deliveryAssignment.setUpdatedAt(LocalDateTime.now());
        }

        if(!deliveryAssignment.getDeliveryMan().equals(deliveryMan)) {
            deliveryAssignment.setDeliveryMan(deliveryMan);
            deliveryAssignment.setUpdatedAt(LocalDateTime.now());
        }

        return deliveryAssignmentDtoMapper.toDeliveryAssignmentDto(deliveryDao.updateDeliveryAssignment(deliveryAssignment));
    }
    @Transactional
    @Override
    public DeliveryAssignmentDto authorizePayment(Integer transactionId) {
        EscrowTransaction escrowTransaction = escrowTransactionDao.getEscrowTransaction(transactionId);
        DeliveryAssignment deliveryAssignment = escrowTransaction.getDeliveryAssignment();
        UserAccount buyer = escrowTransaction.getBuyer();
        UserAccount seller = escrowTransaction.getSeller();
        UserAccount deliveryMan = deliveryAssignment.getDeliveryMan();


        LocalDateTime paidAt = LocalDateTime.now();

        escrowTransaction.setStatus(ProductStatus.DELIVERED);

        deliveryAssignment.setDeliveredAt(LocalDateTime.now());

        UserWalletTransaction userWalletTransaction = UserWalletTransaction.builder()
                .userWalletTransactionType(UserWalletTransactionType.ESCROW_PAYMENT)
                .transactionCode(escrowTransaction.getPurchaseCode())
                .transactionAmount(escrowTransaction.getAmount())
                .createdAt(paidAt)
                .userWallet(seller.getUserWallet())
                .balance(seller.getUserWallet().getBalance() + escrowTransaction.getAmount())
                .build();

        userWalletDao.createTransaction(userWalletTransaction);

        return deliveryAssignmentDtoMapper.toDeliveryAssignmentDto(deliveryDao.updateDeliveryAssignment(deliveryAssignment));
    }
}
