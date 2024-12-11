package com.escrow.wazipay.userWallet.entity;

import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_wallet")
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "userWallet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserWalletTransaction> transactions;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
    private Double balance;
}
