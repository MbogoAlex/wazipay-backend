package com.escrow.wazipay.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String pin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Boolean verified;

    private Boolean archived;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserVerification userVerification;

    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserVerification> userVerifications;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserRole> roles;
}
