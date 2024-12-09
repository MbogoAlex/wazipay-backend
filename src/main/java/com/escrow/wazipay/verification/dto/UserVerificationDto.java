package com.escrow.wazipay.verification.dto;

import com.escrow.wazipay.user.entity.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVerificationDto {
    private Integer verificationId;
    private Integer userId;
    private String username;
    private String phoneNumber;
    private String email;
    private String idFront;
    private String idBack;
    private VerificationStatus verificationStatus;
    private LocalDateTime verifiedAt;
}
