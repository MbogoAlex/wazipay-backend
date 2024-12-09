package com.escrow.wazipay.suspension.dao;

import com.escrow.wazipay.suspension.entity.Suspension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SuspensionDaoImpl implements SuspensionDao{
    private final EntityManager entityManager;
    @Autowired
    public SuspensionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Suspension suspendUser(Suspension suspension) {
        entityManager.persist(suspension);
        return suspension;
    }

    @Override
    public Suspension updateSuspension(Suspension suspension) {
        entityManager.merge(suspension);
        return suspension;
    }

    @Override
    public Suspension getSuspension(Integer id) {
        TypedQuery<Suspension> query = entityManager.createQuery("from Suspension where id = :id", Suspension.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Suspension> getUserSuspensions(Integer userId) {
        TypedQuery<Suspension> query = entityManager.createQuery("from Suspension where user.id = :id", Suspension.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }
}
