package com.escrow.wazipay.user.dto.mapper;

import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.suspension.dto.mapper.SuspensionMapperDto;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.verification.dto.mapper.UserVerificationDtoMapper;

import java.util.stream.Collectors;

public class UserDtoMapper {
    public UserDto toUserDto(UserAccount user, Settings settings) {
        UserVerificationDtoMapper userVerificationDtoMapper = new UserVerificationDtoMapper();
        SuspensionMapperDto suspensionMapperDto = new SuspensionMapperDto();

        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(userRole -> userRole.getRole().name()).collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .verified(user.getVerified())
                .verifiedAt(user.getVerifiedAt())
                .verificationStatus(user.getVerificationStatus())
                .verificationDetails(userVerificationDtoMapper.toUserverificationDto(user.getUserVerification(), settings))
                .suspended(user.getSuspended())
                .suspensions(user.getSuspensions().stream().map(suspensionMapperDto::toSuspensionDto).collect(Collectors.toList()))
                .archived(user.getArchived())
                .archivedAt(user.getArchivedAt())
                .build();
    }
}
