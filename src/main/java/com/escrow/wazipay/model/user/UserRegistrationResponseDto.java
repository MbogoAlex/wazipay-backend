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
public class UserRegistrationResponseDto {
    private Integer id;
    private String username;
    private String email;
    private List<UserRole> roles; // Assuming roles are represented as strings
    private Boolean verified;
}