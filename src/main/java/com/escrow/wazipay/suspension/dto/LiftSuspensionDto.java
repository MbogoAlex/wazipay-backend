package com.escrow.wazipay.suspension.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiftSuspensionDto {
    private Integer suspensionId;
    private String suspensionListReason;
}