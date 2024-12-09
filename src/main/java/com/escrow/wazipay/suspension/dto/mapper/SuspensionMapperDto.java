package com.escrow.wazipay.suspension.dto.mapper;

import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.entity.Suspension;

public class SuspensionMapperDto {
    public SuspensionDto toSuspensionDto(Suspension suspension) {
        return SuspensionDto.builder()
                .suspensionId(suspension.getId())
                .userId(suspension.getUser().getId())
                .username(suspension.getUser().getName())
                .email(suspension.getUser().getEmail())
                .phoneNumber(suspension.getUser().getPhoneNumber())
                .suspendedAt(suspension.getSuspendedAt())
                .suspensionReason(suspension.getSuspensionReason())
                .suspensionLifted(suspension.getSuspensionLifted())
                .suspensionLiftReason(suspension.getSuspensionLiftReason())
                .suspensionLiftedAt(suspension.getSuspensionLiftedAt())
                .build();
    }
}
