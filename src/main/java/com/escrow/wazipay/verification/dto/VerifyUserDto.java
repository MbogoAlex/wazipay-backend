package com.escrow.wazipay.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyUserDto {
    private Integer userId;
    private String businessName;
    private String businessLocation;
}
