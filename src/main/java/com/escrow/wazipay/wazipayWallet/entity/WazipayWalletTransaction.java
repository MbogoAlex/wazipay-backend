package com.escrow.wazipay.wazipayWallet.entity;

import com.escrow.wazipay.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wazipay_wallet_transaction")
public class WazipayWalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private WazipayWalletTransactionType wazipayWalletTransactionType;
    @Column(name = "transaction_code", unique = true)
    private String transactionCode;
    @Column(name = "transaction_amount")
    private Double transactionAmount;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WazipayWallet wazipayWallet;
}
