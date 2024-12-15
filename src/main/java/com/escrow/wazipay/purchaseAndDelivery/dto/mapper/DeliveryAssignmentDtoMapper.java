package com.escrow.wazipay.purchaseAndDelivery.dto.mapper;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;
import com.escrow.wazipay.purchaseAndDelivery.dto.DeliveryAssignmentDto;

public class DeliveryAssignmentDtoMapper {
    EscrowUserDtoMapper escrowUserDtoMapper = new EscrowUserDtoMapper();
    public DeliveryAssignmentDto toDeliveryAssignmentDto(DeliveryAssignment deliveryAssignment) {
        return DeliveryAssignmentDto.builder()
                .id(deliveryAssignment != null ? deliveryAssignment.getId() : null)
                .deliveryMan(deliveryAssignment != null ? escrowUserDtoMapper.toDeliveryManDto(deliveryAssignment) : null)
                .deliveryStatus(deliveryAssignment != null ? deliveryAssignment.getStatus() : null)
                .deliveryLocation(deliveryAssignment != null ? deliveryAssignment.getDeliveryLocation() : null)
                .createdAt(deliveryAssignment != null ? deliveryAssignment.getCreatedAt() : null)
                .updatedAt(deliveryAssignment != null ? deliveryAssignment.getUpdatedAt() : null)
                .deliveredAt(deliveryAssignment != null ? deliveryAssignment.getDeliveredAt() : null)
                .build();


    }
}
