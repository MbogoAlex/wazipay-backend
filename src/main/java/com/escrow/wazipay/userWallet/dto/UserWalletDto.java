package com.escrow.wazipay.userWallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWalletDto {
    private Integer walletId;
    private Integer userId;
    private String username;
    private Double walletBalance;
}
