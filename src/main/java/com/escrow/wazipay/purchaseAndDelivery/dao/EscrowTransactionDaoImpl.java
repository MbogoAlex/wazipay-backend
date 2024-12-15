package com.escrow.wazipay.purchaseAndDelivery.dao;

import com.escrow.wazipay.purchaseAndDelivery.entity.EscrowTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EscrowTransactionDaoImpl implements EscrowTransactionDao{
    private final EntityManager entityManager;
    @Autowired
    public EscrowTransactionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public EscrowTransaction createEscrowTransaction(
            EscrowTransaction escrowTransaction
    ) {
        entityManager.persist(escrowTransaction);
        return escrowTransaction;
    }

    @Override
    public EscrowTransaction updateEscrowTransaction(EscrowTransaction escrowTransaction) {
        entityManager.merge(escrowTransaction);
        return escrowTransaction;
    }

    @Override
    public EscrowTransaction getEscrowTransaction(Integer id) {
        TypedQuery<EscrowTransaction> query = entityManager.createQuery("from EscrowTransaction where id = :id", EscrowTransaction.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public Boolean existsByPurchaseCode(String purchaseCode) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(e) FROM EscrowTransaction e WHERE e.purchaseCode = :purchaseCode", Long.class);
        query.setParameter("purchaseCode", purchaseCode);
        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public List<EscrowTransaction> getSellerEscrowTransactions(Integer userId) {
        TypedQuery<EscrowTransaction> query = entityManager.createQuery("from EscrowTransaction where seller.id = :userId", EscrowTransaction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<EscrowTransaction> getBuyerEscrowTransactions(Integer userId) {
        TypedQuery<EscrowTransaction> query = entityManager.createQuery("from EscrowTransaction where buyer.id = :userId", EscrowTransaction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }


    @Override
    public List<EscrowTransaction> getAllTransactions() {
        TypedQuery<EscrowTransaction> query = entityManager.createQuery("from EscrowTransaction", EscrowTransaction.class);
        return query.getResultList();
    }
}
