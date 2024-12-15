package com.escrow.wazipay.purchaseAndDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerDto {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
}
