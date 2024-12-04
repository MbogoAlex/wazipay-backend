package com.escrow.wazipay.verification.dto.mapper;

import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.media.entity.VerificationImage;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
import com.escrow.wazipay.verification.entity.UserVerification;
import java.util.List;

public class UserVerificationDtoMapper {
    public UserVerificationDto toUserverificationDto(UserVerification userVerification, Settings settings) {
        String domain = settings.getValue();
        String frontIdUrl = null;
        String backIdUrl = null;

        if (userVerification == null) {
            // Return a UserVerificationDto with null fields for userVerification-dependent properties
            return UserVerificationDto.builder()
                    .verificationId(null)
                    .userId(null)
                    .username(null)
                    .phoneNumber(null)
                    .email(null)
                    .idFront(null)
                    .idBack(null)
                    .businessName(null)
                    .businessLocation(null)
                    .verificationStatus(null)
                    .verifiedAt(null)
                    .build();
        }

        if(userVerification.getIdImages() != null) {
            // Process normally if userVerification is not null
            if (!userVerification.getIdImages().isEmpty()) {
                List<VerificationImage> images = userVerification.getIdImages();
                frontIdUrl = domain + "/images/" + images.get(0).getName();
                backIdUrl = domain + "/images/" + images.get(1).getName();
            }
        }


        return UserVerificationDto.builder()
                .verificationId(userVerification.getId())
                .userId(userVerification.getUser().getId())
                .username(userVerification.getUser().getName())
                .phoneNumber(userVerification.getUser().getPhoneNumber())
                .email(userVerification.getUser().getEmail())
                .idFront(frontIdUrl)
                .idBack(backIdUrl)
                .businessName(userVerification.getBusinessName())
                .businessLocation(userVerification.getBusinessLocation())
                .verificationStatus(userVerification.getUser().getVerificationStatus())
                .verifiedAt(userVerification.getUser().getVerifiedAt())
                .build();
    }

}
