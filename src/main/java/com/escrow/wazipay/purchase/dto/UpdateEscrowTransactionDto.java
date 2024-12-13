package com.escrow.wazipay.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEscrowTransactionDto {
    private Integer id;
    private String productName;
    private String productDescription;
    private Double amount;

}
