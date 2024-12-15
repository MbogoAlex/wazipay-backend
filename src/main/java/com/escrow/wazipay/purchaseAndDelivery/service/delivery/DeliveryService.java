package com.escrow.wazipay.purchaseAndDelivery.service.delivery;

import com.escrow.wazipay.purchaseAndDelivery.dto.CreateDeliveryAssignmentDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.DeliveryAssignmentDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.UpdateDeliveryAssignmentDto;

public interface DeliveryService {
    DeliveryAssignmentDto createDeliveryAssignment(CreateDeliveryAssignmentDto createDeliveryAssignmentDto);
    DeliveryAssignmentDto updateDeliveryAssignment(UpdateDeliveryAssignmentDto updateDeliveryAssignmentDto);

    DeliveryAssignmentDto authorizePayment(Integer transactionId);
}
