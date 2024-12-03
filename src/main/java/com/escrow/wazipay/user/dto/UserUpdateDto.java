package com.escrow.wazipay.user.dto;

import com.escrow.wazipay.user.entity.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String email;
    private String verificationStatus;

}
