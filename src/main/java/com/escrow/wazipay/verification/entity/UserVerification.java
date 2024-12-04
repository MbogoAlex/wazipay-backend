package com.escrow.wazipay.verification.entity;

import com.escrow.wazipay.media.entity.VerificationImage;
import com.escrow.wazipay.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_verification")
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String businessName;
    private String businessLocation;
    @OneToOne(mappedBy = "userVerification", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private VerificationImage idFront;
    @OneToOne(mappedBy = "userVerification", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private VerificationImage idBack;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
