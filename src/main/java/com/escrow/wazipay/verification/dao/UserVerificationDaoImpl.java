package com.escrow.wazipay.verification.dao;

import com.escrow.wazipay.verification.entity.UserVerification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserVerificationDaoImpl implements UserVerificationDao{
    private final EntityManager entityManager;
    @Autowired
    public UserVerificationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public UserVerification uploadUserVerificationDetails(UserVerification userVerification) {
        entityManager.persist(userVerification);
        return userVerification;
    }

    @Override
    public UserVerification getUserVerificationDetails(Integer userId) {
        TypedQuery<UserVerification> query = entityManager.createQuery("from UserVerification where user.id = :userId", UserVerification.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}
