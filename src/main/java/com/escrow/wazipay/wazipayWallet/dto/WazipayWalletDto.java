package com.escrow.wazipay.wazipayWallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WazipayWalletDto {
    private Integer id;
    private Double balance;
}
