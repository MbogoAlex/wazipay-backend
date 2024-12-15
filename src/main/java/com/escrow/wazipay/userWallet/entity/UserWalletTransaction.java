package com.escrow.wazipay.userWallet.entity;

import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransactionType;
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
@Table(name = "user_wallet_transaction")
public class UserWalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private UserWalletTransactionType userWalletTransactionType;
    @Column(name = "transaction_code")
    private String transactionCode;
    @Column(name = "transaction_amount")
    private Double transactionAmount;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWallet userWallet;
    private Double balance;
}
