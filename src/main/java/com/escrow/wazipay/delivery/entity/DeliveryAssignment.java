package com.escrow.wazipay.delivery.entity;

import com.escrow.wazipay.purchaseAndDelivery.entity.EscrowTransaction;
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
@Table(name = "delivery_assignment")
public class DeliveryAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false) // Foreign key to EscrowTransaction
    private EscrowTransaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_man_id", nullable = false) // Foreign key to User table
    private UserAccount deliveryMan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deliver_to")
    private String deliveryLocation;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
}
