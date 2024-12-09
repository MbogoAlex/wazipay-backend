package com.escrow.wazipay.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessOwnerDto {
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;
    private LocalDateTime createdAt;
    private Boolean archived;
    private LocalDateTime archivedAt;
}
