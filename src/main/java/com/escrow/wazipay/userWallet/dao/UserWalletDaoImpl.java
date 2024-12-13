package com.escrow.wazipay.userWallet.dao;

import com.escrow.wazipay.userWallet.entity.UserWallet;
import com.escrow.wazipay.userWallet.entity.UserWalletTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserWalletDaoImpl implements UserWalletDao{
    private final EntityManager entityManager;
    @Autowired
    public UserWalletDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserWallet createUserWallet(UserWallet userWallet) {
        entityManager.persist(userWallet);
        return userWallet;
    }

    @Override
    public UserWallet updateUserWallet(UserWallet userWallet) {
        entityManager.merge(userWallet);
        return userWallet;
    }

    @Override
    public UserWallet getUserWallet(Integer userId) {
        TypedQuery<UserWallet> query = entityManager.createQuery("from UserWallet where user.id = :userId", UserWallet.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    public UserWallet getWallet(Integer id) {
        TypedQuery<UserWallet> query = entityManager.createQuery("from UserWallet where id = :id", UserWallet.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<UserWallet> getWallets() {
        TypedQuery<UserWallet> query = entityManager.createQuery("from UserWallet", UserWallet.class);
        return query.getResultList();
    }

    @Override
    public UserWalletTransaction createTransaction(UserWalletTransaction userWalletTransaction) {
        entityManager.persist(userWalletTransaction);

        return userWalletTransaction;
    }

    @Override
    public UserWalletTransaction updateTransaction(UserWalletTransaction userWalletTransaction) {
        entityManager.merge(userWalletTransaction);
        return userWalletTransaction;
    }

    @Override
    public UserWalletTransaction getTransactionById(Integer id) {
        TypedQuery<UserWalletTransaction> query = entityManager.createQuery("from UserWalletTransaction where id = :id", UserWalletTransaction.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UserWalletTransaction getTransactionByCode(String code) {
        TypedQuery<UserWalletTransaction> query = entityManager.createQuery("from UserWalletTransaction where transactionCode = :code", UserWalletTransaction.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    @Override
    public UserWalletTransaction getUserTransactions(Integer userId) {
        TypedQuery<UserWalletTransaction> query = entityManager.createQuery("from UserWalletTransaction where userWallet.user.id = :userId", UserWalletTransaction.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}
