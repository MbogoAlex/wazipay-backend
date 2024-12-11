package com.escrow.wazipay.suspension.entity;

import com.escrow.wazipay.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "suspension")
public class Suspension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "suspended_at")
    private LocalDateTime suspendedAt;
    @Column(name = "suspension_reason")
    private String suspensionReason;
    @Column(name = "suspension_lifted")
    private Boolean suspensionLifted;
    @Column(name = "suspension_lifted_at")
    private LocalDateTime suspensionLiftedAt;
    @Column(name = "suspension_lift_reason")
    private String suspensionLiftReason;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
}
