package com.escrow.wazipay.wazipayWallet.dto;

import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
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
public class UpdateWazipayTransactionDto {
    private Integer transactionId;
    private WazipayWalletTransactionType transactionType;
    private Double amount;
    private String transactionCode;
    private Integer userId;
    private LocalDateTime createdAt;
}
