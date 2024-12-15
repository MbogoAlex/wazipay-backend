package com.escrow.wazipay.purchaseAndDelivery.dao;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;

public interface DeliveryDao {
    DeliveryAssignment createDeliveryAssignment(DeliveryAssignment deliveryAssignment);
    DeliveryAssignment updateDeliveryAssignment(DeliveryAssignment deliveryAssignment);
    DeliveryAssignment getDeliveryAssignment(Integer id);
}
