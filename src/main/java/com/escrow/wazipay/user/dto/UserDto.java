package com.escrow.wazipay.user.dto;

import com.escrow.wazipay.user.entity.VerificationStatus;
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
public class UserDto {
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String email;
    private List<String> roles;
    private LocalDateTime createdAt;
    private VerificationStatus verificationStatus;
    private Boolean archived;
    private LocalDateTime archivedAt;
}