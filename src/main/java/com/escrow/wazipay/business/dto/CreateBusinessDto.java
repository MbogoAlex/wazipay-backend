package com.escrow.wazipay.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBusinessDto {
    private String businessName;
    private String businessDescription;
    private String businessLocation;
}
