package com.escrow.wazipay.purchaseAndDelivery.dao;

import com.escrow.wazipay.purchaseAndDelivery.entity.EscrowTransaction;
import com.escrow.wazipay.purchaseAndDelivery.entity.ProductStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<EscrowTransaction> getBuyerEscrowTransactions(Integer userId, String status, String searchQuery, LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EscrowTransaction> cq = cb.createQuery(EscrowTransaction.class);
        Root<EscrowTransaction> root = cq.from(EscrowTransaction.class);

        // Build predicates
        List<Predicate> predicates = new ArrayList<>();

        // Buyer ID condition
        if (userId != null) {
            predicates.add(cb.equal(root.get("buyer").get("id"), userId));
        }

        // Status condition
        if (status != null && !status.isEmpty()) {
            predicates.add(cb.equal(root.get("status"), ProductStatus.valueOf(status)));
        }

        // Search query condition (searching in product name and description)
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate productNamePredicate = cb.like(cb.lower(root.get("productName")), "%" + searchQuery.toLowerCase() + "%");
            Predicate productDescriptionPredicate = cb.like(cb.lower(root.get("productDescription")), "%" + searchQuery.toLowerCase() + "%");
            predicates.add(cb.or(productNamePredicate, productDescriptionPredicate));
        }

        // Start date condition
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
        }

        // End date condition
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
        }

        // Combine all predicates
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Sort by createdAt in descending order
        cq.orderBy(cb.desc(root.get("createdAt")));

        // Execute query
        TypedQuery<EscrowTransaction> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<EscrowTransaction> getAllTransactions() {
        TypedQuery<EscrowTransaction> query = entityManager.createQuery("from EscrowTransaction", EscrowTransaction.class);
        return query.getResultList();
    }
}
