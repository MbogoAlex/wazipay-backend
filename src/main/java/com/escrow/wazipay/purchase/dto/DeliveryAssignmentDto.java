package com.escrow.wazipay.purchase.dto;

import com.escrow.wazipay.delivery.entity.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAssignmentDto {
    private Integer id;
    private DeliveryManDto deliveryMan;
    private DeliveryStatus deliveryStatus;
    private String deliveryLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deliveredAt;
}
