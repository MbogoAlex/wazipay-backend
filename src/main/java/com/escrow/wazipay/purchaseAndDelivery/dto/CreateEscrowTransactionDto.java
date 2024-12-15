package com.escrow.wazipay.purchaseAndDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEscrowTransactionDto {
    private Integer businessId;
    private String productName;
    private String productDescription;
    private Double amount;

}
