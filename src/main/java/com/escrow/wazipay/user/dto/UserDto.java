package com.escrow.wazipay.user.dto;

import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.user.entity.VerificationStatus;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
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
    private Boolean verified;
    private LocalDateTime verifiedAt;
    private VerificationStatus verificationStatus;
    private UserVerificationDto verificationDetails;
    private Boolean suspended;
    private List<SuspensionDto> suspensions;
    private Boolean archived;
    private LocalDateTime archivedAt;
}
