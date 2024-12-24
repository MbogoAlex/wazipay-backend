package com.escrow.wazipay.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSetPinRequestDto {
    private Integer userId;
    private String pin;
}
