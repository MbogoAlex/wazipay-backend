package com.escrow.wazipay.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RejectUserDto {
    private Integer adminId;
    private Integer applicantId;
    private String message;
}
