package com.escrow.wazipay.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDto {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
}
