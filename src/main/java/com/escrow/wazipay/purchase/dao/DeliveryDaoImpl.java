package com.escrow.wazipay.purchase.dao;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryDaoImpl implements DeliveryDao{
    public final EntityManager entityManager;
    @Autowired
    public DeliveryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DeliveryAssignment createDeliveryAssignment(DeliveryAssignment deliveryAssignment) {
        entityManager.persist(deliveryAssignment);
        return deliveryAssignment;
    }

    @Override
    public DeliveryAssignment updateDeliveryAssignment(DeliveryAssignment deliveryAssignment) {
        entityManager.merge(deliveryAssignment);
        return deliveryAssignment;
    }

    @Override
    public DeliveryAssignment getDeliveryAssignment(Integer id) {
        TypedQuery<DeliveryAssignment> query = entityManager.createQuery("from DeliveryAssignment where id = :id", DeliveryAssignment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
