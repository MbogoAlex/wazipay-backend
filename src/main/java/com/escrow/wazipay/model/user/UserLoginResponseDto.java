package com.escrow.wazipay.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponseDto {
    private Integer id;
    private String username;
    private String email;
    private List<UserRole> roles;
    private Boolean verified;
}
