package com.escrow.wazipay.purchase.service.buyer;

import com.escrow.wazipay.purchase.dao.EscrowTransactionDao;
import com.escrow.wazipay.purchase.dto.EscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.mapper.EscrowTransactionDtoMapper;
import com.escrow.wazipay.purchase.entity.EscrowTransaction;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.userWallet.dao.UserWalletDao;
import com.escrow.wazipay.userWallet.entity.UserWallet;
import com.escrow.wazipay.userWallet.entity.UserWalletTransaction;
import com.escrow.wazipay.userWallet.entity.UserWalletTransactionType;
import com.escrow.wazipay.wazipayWallet.dao.WazipayWalletDao;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BuyerServiceImpl implements BuyerService{
    private final EscrowTransactionDtoMapper escrowTransactionDtoMapper = new EscrowTransactionDtoMapper();
    private final EscrowTransactionDao escrowTransactionDao;
    private final UserWalletDao userWalletDao;
    private final UserDao userDao;
    private final WazipayWalletDao wazipayWalletDao;
    @Autowired
    public BuyerServiceImpl(
            EscrowTransactionDao escrowTransactionDao,
            UserWalletDao userWalletDao,
            UserDao userDao,
            WazipayWalletDao wazipayWalletDao
    ) {
        this.escrowTransactionDao = escrowTransactionDao;
        this.userWalletDao = userWalletDao;
        this.userDao = userDao;
        this.wazipayWalletDao = wazipayWalletDao;
    }
    @Transactional
    @Override
    public EscrowTransactionDto directDepositToEscrow(Integer transactionId, Integer buyerId) {

        UserAccount userAccount = userDao.getUserByUserId(buyerId);

        UserWallet userWallet = userAccount.getUserWallet();

        WazipayWallet wazipayWallet = wazipayWalletDao.getWallets().get(0);

        LocalDateTime paidAt = LocalDateTime.now();

        EscrowTransaction escrowTransaction = escrowTransactionDao.getEscrowTransaction(transactionId);
        escrowTransaction.setBuyer(userAccount);
        escrowTransaction.setPaid(true);
        escrowTransaction.setPaidAt(paidAt);

        UserWalletTransaction userWalletTransaction = UserWalletTransaction.builder()
                .userWalletTransactionType(UserWalletTransactionType.ESCROW_DEPOSIT)
                .transactionCode(escrowTransaction.getPurchaseCode())
                .transactionAmount(escrowTransaction.getAmount())
                .createdAt(paidAt)
                .userWallet(userWallet)
                .balance(userWallet.getBalance())
                .build();

        wazipayWallet.setBalance(wazipayWallet.getBalance() + escrowTransaction.getAmount());

        wazipayWalletDao.updateWallet(wazipayWallet);

        userWalletDao.createTransaction(userWalletTransaction);


        return escrowTransactionDtoMapper.toEscrowTransactionDto(escrowTransactionDao.updateEscrowTransaction(escrowTransaction));
    }

    @Override
    public EscrowTransactionDto getEscrowTransaction(Integer transactionId) {
        return escrowTransactionDtoMapper.toEscrowTransactionDto(escrowTransactionDao.getEscrowTransaction(transactionId));
    }
}
