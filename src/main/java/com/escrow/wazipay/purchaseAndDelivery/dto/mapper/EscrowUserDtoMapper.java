package com.escrow.wazipay.purchaseAndDelivery.dto.mapper;

import com.escrow.wazipay.delivery.entity.DeliveryAssignment;
import com.escrow.wazipay.purchaseAndDelivery.dto.BuyerDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.DeliveryManDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.SellerDto;
import com.escrow.wazipay.user.entity.UserAccount;

public class EscrowUserDtoMapper {

    public BuyerDto toBuyerDto(UserAccount user) {
        Integer userId = null;
        String username = null;
        String email = null;
        String phoneNumber = null;

        if(user != null) {
            userId = user.getId();
            username = user.getName();
            email = user.getEmail();
            phoneNumber = user.getPhoneNumber();
        }
        return BuyerDto.builder()
                .id(userId)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
    public SellerDto toSellerDto(UserAccount user) {
        return SellerDto.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public DeliveryManDto toDeliveryManDto(DeliveryAssignment deliveryAssignment) {
        return DeliveryManDto.builder()
                .id(deliveryAssignment.getDeliveryMan().getId())
                .username(deliveryAssignment.getDeliveryMan().getName())
                .email(deliveryAssignment.getDeliveryMan().getEmail())
                .phoneNumber(deliveryAssignment.getDeliveryMan().getPhoneNumber())
                .build();
    }


}
