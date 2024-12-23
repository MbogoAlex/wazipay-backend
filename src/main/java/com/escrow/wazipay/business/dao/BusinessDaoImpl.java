package com.escrow.wazipay.business.dao;

import com.escrow.wazipay.business.entity.Business;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusinessDaoImpl implements BusinessDao{
    private final EntityManager entityManager;
    @Autowired
    public BusinessDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Business createBusiness(Business business) {
        entityManager.persist(business);
        return business;
    }

    @Override
    public Business updateBusiness(Business business) {
        entityManager.merge(business);
        return business;
    }

    @Override
    public Business getBusinessById(Integer businessId) {
        TypedQuery<Business> query = entityManager.createQuery("from Business where id = :businessId", Business.class);
        query.setParameter("businessId", businessId);
        return query.getSingleResult();
    }

    @Override
    public List<Business> getUserBusinesses(Integer userId) {
        TypedQuery<Business> query = entityManager.createQuery("from Business where user.id = :userId", Business.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Business> getBusinesses(Integer userId, String name, String location, String owner) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> query = cb.createQuery(Business.class);
        Root<Business> root = query.from(Business.class);

        // Create a list to store predicates
        List<Predicate> predicates = new ArrayList<>();

        // Add predicate for userId
        if (userId != null) {
            predicates.add(cb.equal(root.get("user").get("id"), userId));
        }

        // Add predicate for business name
        if (name != null && !name.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("businessName")), "%" + name.toLowerCase() + "%"));
        }

        // Add predicate for business location
        if (location != null && !location.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("businessLocation")), "%" + location.toLowerCase() + "%"));
        }

        // Add predicate for owner (user.name, user.email, or user.phoneNumber)
        if (owner != null && !owner.trim().isEmpty()) {
            Predicate ownerPredicate = cb.or(
                    cb.like(cb.lower(root.get("user").get("name")), "%" + owner.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("user").get("email")), "%" + owner.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("user").get("phoneNumber")), "%" + owner.toLowerCase() + "%")
            );
            predicates.add(ownerPredicate);
        }

        // Build the query
        query.select(root).where(predicates.toArray(new Predicate[0]));

        // Execute the query
        return entityManager.createQuery(query).getResultList();
    }

}
