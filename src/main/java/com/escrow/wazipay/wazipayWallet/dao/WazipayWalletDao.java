package com.escrow.wazipay.wazipayWallet.dao;

import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
import java.util.List;

public interface WazipayWalletDao {
    WazipayWallet updateWallet(WazipayWallet wazipayWallet);
    WazipayWallet getWallet(Integer id);
    WazipayWalletTransaction addTransaction(WazipayWalletTransaction wazipayWalletTransaction);
    WazipayWalletTransaction updateTransaction(WazipayWalletTransaction wazipayWalletTransaction);

    WazipayWalletTransaction getTransactionByTransactionId(Integer id);
    WazipayWalletTransaction getTransactionByTransactionCode(String code);
    List<WazipayWalletTransaction> getUserTransactions(Integer userId);
    List<WazipayWalletTransaction> getAllWazipayWalletTransactions();
}
