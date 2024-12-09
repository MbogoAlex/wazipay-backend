package com.escrow.wazipay.suspension.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuspensionDto {
    private Integer suspensionId;
    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDateTime suspendedAt;
    private String suspensionReason;
    private Boolean suspensionLifted;
    private String suspensionLiftReason;
    private LocalDateTime suspensionLiftedAt;
}
