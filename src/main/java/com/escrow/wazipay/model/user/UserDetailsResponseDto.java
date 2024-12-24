package com.escrow.wazipay.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponseDto {
    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private Boolean archived;
    private LocalDateTime archivedAt;
    private Boolean verified;
    private LocalDateTime verifiedAt;
    private VerificationStatus verificationStatus;
    private String idFront;
    private String idBack;
}
