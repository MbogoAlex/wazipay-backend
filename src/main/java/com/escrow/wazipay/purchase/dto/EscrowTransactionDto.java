package com.escrow.wazipay.purchase.dto;

import com.escrow.wazipay.purchase.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EscrowTransactionDto {
    private Integer id;
    private String purchaseCode;
    private String businessName;
    private String productName;
    private String productDescription;
    private Double amount;
    private Boolean paid;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SellerDto seller;
    private BuyerDto buyer;
    private DeliveryAssignmentDto deliveryDetails;
    private String paymentLink;
}
