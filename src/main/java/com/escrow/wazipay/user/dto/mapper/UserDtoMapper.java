package com.escrow.wazipay.user.dto.mapper;

import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.entity.User;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
import com.escrow.wazipay.verification.dto.mapper.UserVerificationDtoMapper;

import java.util.stream.Collectors;

public class UserDtoMapper {
    public UserDto toUserDto(User user, Settings settings) {
        UserVerificationDtoMapper userVerificationDtoMapper = new UserVerificationDtoMapper();

        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(userRole -> userRole.getRole().name()).collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .verifiedAt(user.getVerifiedAt())
                .verificationStatus(user.getVerificationStatus())
                .verificationDetails(userVerificationDtoMapper.toUserverificationDto(user.getUserVerification(), settings))
                .archived(user.getArchived())
                .archivedAt(user.getArchivedAt())
                .build();
    }
}
