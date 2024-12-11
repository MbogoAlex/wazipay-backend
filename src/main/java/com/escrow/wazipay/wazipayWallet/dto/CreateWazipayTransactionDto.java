package com.escrow.wazipay.wazipayWallet.dto;

import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWazipayTransactionDto {
    private WazipayWalletTransaction transactionType;
    private String amount;
    private String transactionCode;
    private String userId;
    private LocalDateTime createdAt;
}
