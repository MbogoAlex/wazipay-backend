package com.escrow.wazipay.wazipayWallet.dao;

import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WazipayWalletDaoImpl implements WazipayWalletDao{
    private final EntityManager entityManager;
    @Autowired
    public WazipayWalletDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WazipayWallet updateWallet(WazipayWallet wazipayWallet) {
        entityManager.merge(wazipayWallet);
        return wazipayWallet;
    }

    @Override
    public WazipayWallet getWallet(Integer id) {
        TypedQuery<WazipayWallet> query = entityManager.createQuery("from WazipayWallet where id = :id", WazipayWallet.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<WazipayWallet> getWallets() {
        TypedQuery<WazipayWallet> query = entityManager.createQuery("from WazipayWallet", WazipayWallet.class);
        return query.getResultList();
    }

    @Override
    public WazipayWalletTransaction addTransaction(WazipayWalletTransaction wazipayWalletTransaction) {
        entityManager.persist(wazipayWalletTransaction);
        return wazipayWalletTransaction;
    }

    @Override
    public WazipayWalletTransaction updateTransaction(WazipayWalletTransaction wazipayWalletTransaction) {
        entityManager.merge(wazipayWalletTransaction);
        return wazipayWalletTransaction;
    }

    @Override
    public WazipayWalletTransaction getTransactionByTransactionId(Integer id) {
        TypedQuery<WazipayWalletTransaction> query = entityManager.createQuery("from WazipayWalletTransaction where id = :id", WazipayWalletTransaction.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public WazipayWalletTransaction getTransactionByTransactionCode(String code) {
        TypedQuery<WazipayWalletTransaction> query = entityManager.createQuery("from WazipayWalletTransaction where transactionCode = :code", WazipayWalletTransaction.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    @Override
    public List<WazipayWalletTransaction> getUserTransactions(Integer userId) {
        TypedQuery<WazipayWalletTransaction> query = entityManager.createQuery("from WazipayWalletTransaction where user.id = :userId", WazipayWalletTransaction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<WazipayWalletTransaction> getAllWazipayWalletTransactions() {
        TypedQuery<WazipayWalletTransaction> query = entityManager.createQuery("from WazipayWalletTransaction", WazipayWalletTransaction.class);
        return query.getResultList();
    }
}
