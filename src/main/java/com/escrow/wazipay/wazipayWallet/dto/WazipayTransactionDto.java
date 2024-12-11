package com.escrow.wazipay.wazipayWallet.dto;

import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WazipayTransactionDto {
    private Integer id;
    private WazipayWalletTransactionType transactionType;
    private String transactionCode;
    private Double amount;
    private LocalDateTime createdAt;
    private UserDto user;
}
