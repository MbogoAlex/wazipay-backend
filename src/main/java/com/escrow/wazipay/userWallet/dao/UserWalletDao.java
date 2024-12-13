package com.escrow.wazipay.userWallet.dao;

import com.escrow.wazipay.userWallet.entity.UserWallet;
import com.escrow.wazipay.userWallet.entity.UserWalletTransaction;

import java.util.List;

public interface UserWalletDao {
    UserWallet createUserWallet(UserWallet userWallet);
    UserWallet updateUserWallet(UserWallet userWallet);

    UserWallet getUserWallet(Integer userId);
    UserWallet getWallet(Integer id);

    List<UserWallet> getWallets();

    UserWalletTransaction createTransaction(UserWalletTransaction userWalletTransaction);
    UserWalletTransaction updateTransaction(UserWalletTransaction userWalletTransaction);
    UserWalletTransaction getTransactionById(Integer id);
    UserWalletTransaction getTransactionByCode(String code);
    UserWalletTransaction getUserTransactions(Integer userId);
}
