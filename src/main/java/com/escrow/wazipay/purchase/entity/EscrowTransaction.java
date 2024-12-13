package com.escrow.wazipay.purchase.entity;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;
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
@Table(name = "escrow_transaction")
public class EscrowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_code", nullable = false, unique = true)
    private String purchaseCode;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = true) // Foreign key to User table
    private UserAccount buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false) // Foreign key to User table
    private UserAccount seller;

    @Column(nullable = false)
    private Double amount;
    private Boolean paid;
    @Column(name = "paidAt")
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DeliveryAssignment deliveryAssignment;
    @Column(name = "payment_link")
    private String paymentLink;
}
