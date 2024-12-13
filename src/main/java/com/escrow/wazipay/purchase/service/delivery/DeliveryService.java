package com.escrow.wazipay.purchase.service.delivery;

import com.escrow.wazipay.purchase.dto.CreateDeliveryAssignmentDto;
import com.escrow.wazipay.purchase.dto.DeliveryAssignmentDto;
import com.escrow.wazipay.purchase.dto.UpdateDeliveryAssignmentDto;

public interface DeliveryService {
    DeliveryAssignmentDto createDeliveryAssignment(CreateDeliveryAssignmentDto createDeliveryAssignmentDto);
    DeliveryAssignmentDto updateDeliveryAssignment(UpdateDeliveryAssignmentDto updateDeliveryAssignmentDto);

    DeliveryAssignmentDto authorizePayment(Integer transactionId);
}
