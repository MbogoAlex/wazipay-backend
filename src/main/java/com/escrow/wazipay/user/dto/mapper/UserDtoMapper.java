package com.escrow.wazipay.user.dto.mapper;

import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.entity.User;

import java.util.stream.Collectors;

public class UserDtoMapper {
    public UserDto toUserDto(User user) {


        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(userRole -> userRole.getRole().name()).collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .verificationStatus(user.getVerificationStatus())
                .archived(user.getArchived())
                .archivedAt(user.getArchivedAt())
                .build();
    }
}
