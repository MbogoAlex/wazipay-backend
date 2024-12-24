package com.escrow.wazipay.model.user;

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
public class UserVerificationDetailsResponseDto {
    private Integer id;
    private Integer userId;
    private String username;
    private String email;
    private Boolean verified;
    private LocalDateTime verifiedAt;
    private List<UserRole> roles;
    private String idFront;
    private String idBack;
    private UserDetailsResponseDto verifiedBy;
}
