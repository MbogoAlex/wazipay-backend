package com.escrow.wazipay.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessDto {
    private Integer id;
    private String businessName;
    private String businessDescription;
    private String businessLocation;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private Boolean archived;
    private LocalDateTime archivedAt;
    private BusinessOwnerDto owner;
}
