package com.escrow.wazipay.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveUserDto {
    private Integer applicantId;
    private String role;
    private String message;
}
