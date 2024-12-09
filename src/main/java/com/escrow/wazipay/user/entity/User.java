package com.escrow.wazipay.user.entity;

import com.escrow.wazipay.business.entity.Business;
import com.escrow.wazipay.suspension.entity.Suspension;
import com.escrow.wazipay.verification.entity.UserVerification;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String pin;
    @Column(name = "verification_status")
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private Boolean archived;
    @Column(name = "archived_at")
    private LocalDateTime archivedAt;
    private Boolean verified;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    private Boolean suspended;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Suspension> suspensions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserVerification userVerification;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Business> businesses = new ArrayList<>();
}
