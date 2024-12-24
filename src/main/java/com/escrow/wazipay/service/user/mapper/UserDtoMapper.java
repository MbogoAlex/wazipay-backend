package com.escrow.wazipay.service.user.mapper;

import com.escrow.wazipay.model.user.*;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public UserDetailsResponseDto userDetailsResponseDto(UserAccount userAccount, String domain) {
        String idFront = null;
        String idBack = null;

        if(userAccount.getUserVerification() != null) {
            List<VerificationFile> verificationFiles = new ArrayList<>(userAccount.getUserVerification().getFiles());
            idFront = domain + "/images/" + verificationFiles.get(0).getName();
            idBack = domain + "/images/" + verificationFiles.get(0).getName();
        }

        return UserDetailsResponseDto.builder()
                .userId(userAccount.getId())
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .createdAt(userAccount.getCreatedAt())
                .archived(userAccount.getArchived())
                .verified(userAccount.getVerified())
                .verifiedAt(userAccount.getArchivedAt())
                .verificationStatus(userAccount.getVerificationStatus())
                .idFront(idFront)
                .idBack(idBack)
                .build();
    }

    public UserVerificationDetailsResponseDto userVerificationDetailsDto(UserVerification userVerification, String domain) {

        List<VerificationFile> verificationFiles = userVerification.getFiles();

        String idFront = domain + "/images/" + verificationFiles.get(0).getName();
        String idBack = domain + "/images/" + verificationFiles.get(0).getName();

        return UserVerificationDetailsResponseDto.builder()
                .id(userVerification.getId())
                .userId(userVerification.getUserAccount().getId())
                .username(userVerification.getUserAccount().getUsername())
                .email(userVerification.getUserAccount().getEmail())
                .verified(userVerification.getVerified())
                .verifiedAt(userVerification.getVerifiedAt())
                .roles(userVerification.getUserAccount().getRoles())
                .idFront(idFront)
                .idBack(idBack)
                .verifiedBy(userDetailsResponseDto(userVerification.getUserAccount(), domain))
                .build();
    }
}
