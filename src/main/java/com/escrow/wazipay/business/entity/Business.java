package com.escrow.wazipay.business.entity;

import com.escrow.wazipay.user.entity.User;
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
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "business_description")
    private String businessDescription;
    @Column(name = "business_location")
    private String businessLocation;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "archived")
    private Boolean archived;
    @Column(name = "archived_at")
    private LocalDateTime archivedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
