package com.escrow.wazipay.purchase.dto.mapper;

import com.escrow.wazipay.purchase.dto.EscrowTransactionDto;
import com.escrow.wazipay.purchase.entity.EscrowTransaction;
import com.escrow.wazipay.user.entity.UserAccount;

public class EscrowTransactionDtoMapper {
    EscrowUserDtoMapper escrowUserDtoMapper = new EscrowUserDtoMapper();
    DeliveryAssignmentDtoMapper deliveryAssignmentDtoMapper = new DeliveryAssignmentDtoMapper();
    public EscrowTransactionDto toEscrowTransactionDto(EscrowTransaction escrowTransaction) {
        UserAccount buyer = escrowTransaction.getBuyer();


        return EscrowTransactionDto.builder()
                .id(escrowTransaction.getId())
                .purchaseCode(escrowTransaction.getPurchaseCode())
                .productName(escrowTransaction.getProductName())
                .productDescription(escrowTransaction.getProductDescription())
                .paid(escrowTransaction.getPaid())
                .status(escrowTransaction.getStatus())
                .createdAt(escrowTransaction.getCreatedAt())
                .updatedAt(escrowTransaction.getUpdatedAt())
                .amount(escrowTransaction.getAmount())
                .buyer(buyer != null ? escrowUserDtoMapper.toBuyerDto(escrowTransaction.getBuyer()) : null)
                .seller(escrowUserDtoMapper.toSellerDto(escrowTransaction.getSeller()))
                .deliveryDetails(deliveryAssignmentDtoMapper.toDeliveryAssignmentDto(escrowTransaction.getDeliveryAssignment()))
                .paymentLink(escrowTransaction.getPaymentLink())
                .build();

    }
}
