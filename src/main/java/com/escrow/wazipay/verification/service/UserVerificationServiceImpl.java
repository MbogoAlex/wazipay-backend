package com.escrow.wazipay.verification.service;

import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.media.entity.VerificationImage;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.User;
import com.escrow.wazipay.user.entity.VerificationStatus;
import com.escrow.wazipay.verification.dao.UserVerificationDao;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
import com.escrow.wazipay.verification.dto.VerifyUserDto;
import com.escrow.wazipay.verification.dto.mapper.UserVerificationDtoMapper;
import com.escrow.wazipay.verification.entity.UserVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserVerificationServiceImpl implements UserVerificationService{
    private final UserVerificationDtoMapper userVerificationDtoMapper = new UserVerificationDtoMapper();
    private final UserVerificationDao userVerificationDao;
    private final SettingsDao settingsDao;
    private final UserDao userDao;
    @Autowired
    public UserVerificationServiceImpl(
            UserVerificationDao userVerificationDao,
            SettingsDao settingsDao,
            UserDao userDao
    ) {
        this.userVerificationDao = userVerificationDao;
        this.settingsDao = settingsDao;
        this.userDao = userDao;
    }
    @Transactional
    @Override
    public UserVerificationDto uploadUserVerificationDetails(VerifyUserDto verifyUserDto, MultipartFile[] images) throws IOException {
        List<VerificationImage> idImages = new ArrayList<>();
        System.out.println("GETTING USER");
        User user = userDao.getUserByUserId(verifyUserDto.getUserId());
        String basePath = settingsDao.findBySettingsKey("imagePath").getValue();
        Settings domain = settingsDao.findBySettingsKey("domain");

        // Update user's verification status
        user.setVerificationStatus(VerificationStatus.PENDING_VERIFICATION);

        MultipartFile idFront = images[0];
        MultipartFile idBack = images[1];

        // Upload idFront
        String idF_fileName = System.currentTimeMillis() + "_" + idFront.getOriginalFilename().replace(" ", "");
        String idF_filePath = basePath + idF_fileName;
        File idF_uploadFile = new File(idF_filePath);
        idFront.transferTo(idF_uploadFile);

        VerificationImage frontId = VerificationImage.builder()
                .name(idF_fileName)
                .user(user) // Link to the user
                .build();

        // Upload idBack
        String idB_fileName = System.currentTimeMillis() + "_" + idBack.getOriginalFilename().replace(" ", "");
        String idB_filePath = basePath + idB_fileName;
        File idB_uploadFile = new File(idB_filePath);
        idBack.transferTo(idB_uploadFile);

        VerificationImage backId = VerificationImage.builder()
                .name(idB_fileName)
                .user(user) // Link to the user
                .build();

        // Build and link UserVerification object
        UserVerification userVerification = UserVerification.builder()
                .businessName(verifyUserDto.getBusinessName())
                .businessLocation(verifyUserDto.getBusinessLocation())
                .user(user) // Important: Set the user here
                .build();

        // Set userVerification for each image
        frontId.setUserVerification(userVerification);
        backId.setUserVerification(userVerification);

        idImages.add(frontId);
        idImages.add(backId);

        // Set the images in the UserVerification object
        userVerification.setIdImages(idImages);

        // Link verification object to the user
        user.setUserVerification(userVerification);

        // Persist UserVerification object
        return userVerificationDtoMapper.toUserverificationDto(userVerificationDao.uploadUserVerificationDetails(userVerification), domain);
    }


}
