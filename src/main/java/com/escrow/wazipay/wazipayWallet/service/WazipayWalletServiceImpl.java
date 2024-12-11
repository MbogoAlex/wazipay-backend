package com.escrow.wazipay.wazipayWallet.service;

import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.wazipayWallet.dao.WazipayWalletDao;
import com.escrow.wazipay.wazipayWallet.dto.CreateWazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.UpdateWazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.WazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.WazipayWalletDto;
import com.escrow.wazipay.wazipayWallet.dto.mapper.WazipayWalletTransactionMapper;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WazipayWalletServiceImpl implements WazipayWalletService{
    private final WazipayWalletTransactionMapper wazipayWalletTransactionMapper = new WazipayWalletTransactionMapper();
    private final WazipayWalletDao wazipayWalletDao;
    private final SettingsDao settingsDao;

    private final UserDao userDao;
    @Autowired
    public WazipayWalletServiceImpl(
            WazipayWalletDao wazipayWalletDao,
            SettingsDao settingsDao,
            UserDao userDao
    ) {
        this.wazipayWalletDao = wazipayWalletDao;
        this.settingsDao = settingsDao;
        this.userDao = userDao;
    }
    @Transactional
    @Override
    public WazipayWalletDto updateWallet(WazipayWalletDto wazipayWalletDto) {
        WazipayWallet wazipayWallet = wazipayWalletDao.getWallets().get(0);
        wazipayWalletDto.setBalance(wazipayWalletDto.getBalance());

        return wazipayWalletTransactionMapper.toWalletDto(wazipayWalletDao.updateWallet(wazipayWallet));
    }

    @Override
    public WazipayWalletDto getWallet(Integer id) {
        return wazipayWalletTransactionMapper.toWalletDto(wazipayWalletDao.getWallet(1));
    }

    @Override
    public List<WazipayWalletDto> getWallets() {
        return wazipayWalletDao.getWallets().stream().map(wazipayWalletTransactionMapper::toWalletDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public WazipayTransactionDto addTransaction(CreateWazipayTransactionDto wazipayTransactionDto) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        UserAccount userAccount = userDao.getUserByUserId(wazipayTransactionDto.getUserId());
        WazipayWalletTransaction wazipayWalletTransaction = WazipayWalletTransaction.builder()
                .wazipayWalletTransactionType(wazipayTransactionDto.getTransactionType())
                .transactionAmount(wazipayTransactionDto.getAmount())
                .transactionCode(wazipayTransactionDto.getTransactionCode())
                .createdAt(wazipayTransactionDto.getCreatedAt())
                .user(userAccount)
                .build();

        return wazipayWalletTransactionMapper.toWazipayTransactionDto(wazipayWalletTransaction, settings);
    }
    @Transactional
    @Override
    public WazipayTransactionDto updateTransaction(UpdateWazipayTransactionDto wazipayTransactionDto) {
        Settings settings = settingsDao.findBySettingsKey("domain");

        WazipayWalletTransaction wazipayWalletTransaction = wazipayWalletDao.getTransactionByTransactionId(wazipayTransactionDto.getTransactionId());

        if(!wazipayWalletTransaction.getWazipayWalletTransactionType().equals(wazipayTransactionDto.getTransactionType())) {
            wazipayWalletTransaction.setWazipayWalletTransactionType(wazipayTransactionDto.getTransactionType());
        }

        if(!wazipayWalletTransaction.getTransactionAmount().equals(wazipayTransactionDto.getAmount())) {
            wazipayWalletTransaction.setTransactionAmount(wazipayTransactionDto.getAmount());
        }

        return wazipayWalletTransactionMapper.toWazipayTransactionDto(wazipayWalletTransaction, settings);

    }

    @Override
    public WazipayTransactionDto getTransactionByTransactionId(Integer id) {
        Settings settings = settingsDao.findBySettingsKey("domain");

        return wazipayWalletTransactionMapper.toWazipayTransactionDto(wazipayWalletDao.getTransactionByTransactionId(id), settings);
    }

    @Override
    public WazipayTransactionDto getTransactionByTransactionCode(String code) {
        Settings settings = settingsDao.findBySettingsKey("domain");

        return wazipayWalletTransactionMapper.toWazipayTransactionDto(wazipayWalletDao.getTransactionByTransactionCode(code), settings);
    }

    @Override
    public List<WazipayTransactionDto> getUserTransactions(Integer userId) {
        Settings settings = settingsDao.findBySettingsKey("domain");

        return wazipayWalletDao.getUserTransactions(userId).stream().map(transaction -> wazipayWalletTransactionMapper.toWazipayTransactionDto(transaction, settings)).collect(Collectors.toList());
    }

    @Override
    public List<WazipayTransactionDto> getAllWazipayWalletTransactions() {
        Settings settings = settingsDao.findBySettingsKey("domain");

        return wazipayWalletDao.getAllWazipayWalletTransactions().stream().map(transaction -> wazipayWalletTransactionMapper.toWazipayTransactionDto(transaction, settings)).collect(Collectors.toList());
    }
}
