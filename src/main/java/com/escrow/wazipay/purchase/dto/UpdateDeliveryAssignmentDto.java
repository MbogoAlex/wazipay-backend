package com.escrow.wazipay.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDeliveryAssignmentDto {
    private Integer deliveryId;
    private Integer deliveryManId;
    private String deliveryLocation;
}
