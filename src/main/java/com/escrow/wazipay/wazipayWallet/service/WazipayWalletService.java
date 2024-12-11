package com.escrow.wazipay.wazipayWallet.service;

import com.escrow.wazipay.wazipayWallet.dto.CreateWazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.UpdateWazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.WazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.WazipayWalletDto;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;

import java.util.List;

public interface WazipayWalletService {
    WazipayWalletDto updateWallet(WazipayWalletDto wazipayWallet);
    WazipayWalletDto getWallet(Integer id);
    List<WazipayWalletDto> getWallets();
    WazipayTransactionDto addTransaction(CreateWazipayTransactionDto wazipayTransactionDto);
    WazipayTransactionDto updateTransaction(UpdateWazipayTransactionDto wazipayTransactionDto);

    WazipayTransactionDto getTransactionByTransactionId(Integer id);
    WazipayTransactionDto getTransactionByTransactionCode(String code);
    List<WazipayTransactionDto> getUserTransactions(Integer userId);
    List<WazipayTransactionDto> getAllWazipayWalletTransactions();
}
