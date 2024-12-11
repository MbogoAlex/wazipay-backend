package com.escrow.wazipay.user.dao;

import com.escrow.wazipay.user.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    private final EntityManager entityManager;
    @Autowired
    public UserDaoImpl(
            EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }

    @Override
    public UserAccount createAccount(UserAccount user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public UserAccount updateUser(UserAccount user) {
        entityManager.merge(user);
        return user;
    }


    @Override
    public UserAccount getUserByUserId(Integer userId) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where id = :id", UserAccount.class);
        query.setParameter("id", userId);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByPhoneNumber(String phoneNumber) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where phoneNumber = :phoneNumber", UserAccount.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByEmail(String email) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where email = :email", UserAccount.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where phoneNumber = :phoneNumber", UserAccount.class);
        query.setParameter("phoneNumber", phoneNumber);
        return !query.getResultList().isEmpty();
    }

    @Override
    public Boolean existsByEmail(String email) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where email = :email", UserAccount.class);
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }

    @Override
    public List<UserAccount> getAllUsers() {
        TypedQuery<UserAccount> query = entityManager.createQuery("from User", UserAccount.class);
        return query.getResultList();
    }
}
