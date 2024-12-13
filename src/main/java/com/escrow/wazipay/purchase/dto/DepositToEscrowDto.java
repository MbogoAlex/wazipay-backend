package com.escrow.wazipay.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositToEscrowDto {
    private Integer transactionId;
    private Integer buyerId;
    private Integer deliveryId;
}
