package com.escrow.wazipay.business.dto.mapper;

import com.escrow.wazipay.business.dto.BusinessDto;
import com.escrow.wazipay.business.dto.BusinessOwnerDto;
import com.escrow.wazipay.business.entity.Business;

import java.util.stream.Collectors;

public class BusinessDtoMapper {
    public BusinessDto toBusinessDto(Business business) {
        BusinessOwnerDto businessOwner = BusinessOwnerDto.builder()
                .id(business.getUser().getId())
                .username(business.getUser().getName())
                .email(business.getUser().getEmail())
                .roles(business.getUser().getRoles().stream().map(userRole -> userRole.getRole().name()).collect(Collectors.toList()))
                .createdAt(business.getCreatedAt())
                .archived(business.getArchived())
                .archivedAt(business.getArchivedAt())
                .build();

        return BusinessDto.builder()
                .id(business.getId())
                .businessName(business.getBusinessName())
                .businessDescription(business.getBusinessDescription())
                .businessLocation(business.getBusinessLocation())
                .createdAt(business.getCreatedAt())
                .lastUpdate(business.getLastUpdate())
                .archived(business.getArchived())
                .archivedAt(business.getArchivedAt())
                .owner(businessOwner)
                .build();
    }
}
