package com.escrow.wazipay.business.dao;

import com.escrow.wazipay.business.entity.Business;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
