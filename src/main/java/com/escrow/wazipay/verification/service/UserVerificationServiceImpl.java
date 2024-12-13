package com.escrow.wazipay.verification.service;

import com.escrow.wazipay.mail.MailService;
import com.escrow.wazipay.mail.MailStructure;
import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.media.entity.VerificationImage;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.user.entity.UserRole;
import com.escrow.wazipay.user.entity.UserRoleEnum;
import com.escrow.wazipay.user.entity.VerificationStatus;
import com.escrow.wazipay.userWallet.entity.UserWallet;
import com.escrow.wazipay.verification.dao.UserVerificationDao;
import com.escrow.wazipay.verification.dto.ApproveUserDto;
import com.escrow.wazipay.verification.dto.RejectUserDto;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
import com.escrow.wazipay.verification.dto.mapper.UserVerificationDtoMapper;
import com.escrow.wazipay.verification.entity.UserVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserVerificationServiceImpl implements UserVerificationService{
    private final UserVerificationDtoMapper userVerificationDtoMapper = new UserVerificationDtoMapper();
    private final UserVerificationDao userVerificationDao;
    private final SettingsDao settingsDao;
    private final UserDao userDao;
    private final MailService mailService;


    @Autowired
    public UserVerificationServiceImpl(
            UserVerificationDao userVerificationDao,
            SettingsDao settingsDao,
            UserDao userDao,
            MailService mailService
    ) {
        this.userVerificationDao = userVerificationDao;
        this.settingsDao = settingsDao;
        this.userDao = userDao;
        this.mailService = mailService;
    }
    @Transactional
    @Override
    public UserVerificationDto uploadUserVerificationDetails(Integer userId, MultipartFile[] images) throws IOException {
        List<VerificationImage> idImages = new ArrayList<>();
        System.out.println("GETTING USER");
        System.out.println("ARRAY LENGTH: "+ images.length);
        UserAccount user = userDao.getUserByUserId(userId);
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

        MailStructure mailStructure1 = MailStructure
                .builder()
                        .email("gitaumbogo3@gmail.com")
                                .subject("Account verification")
                                        .message("We have received your verification documents and you will be notified when verification is complete.")
                                                .build();

        mailService.sendEmail(mailStructure1);

        MailStructure mailStructure2 = MailStructure
                .builder()
                .email("gitaumbogo3@gmail.com")
                .subject("Account verification Request")
                .message("A new user has submitted his documents for verification.")
                .build();

        mailService.sendEmail(mailStructure2);

        // Persist UserVerification object
        return userVerificationDtoMapper.toUserverificationDto(userVerificationDao.uploadUserVerificationDetails(userVerification), domain);
    }

    @Override
    public UserVerificationDto getUserVerificationDetails(Integer userId) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        return userVerificationDtoMapper.toUserverificationDto(userVerificationDao.getUserVerificationDetails(userId), settings);
    }

    @Transactional
    @Override
    public UserVerificationDto approveUser(ApproveUserDto userDto) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(userDto.getApplicantId());
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(UserRoleEnum.valueOf(userDto.getRole().toUpperCase()))
                .build();


        user.setVerified(true);
        user.setVerificationStatus(VerificationStatus.VERIFIED);

        user.getRoles().add(userRole);
        user.setVerifiedAt(LocalDateTime.now());


        MailStructure mailStructure = MailStructure
                .builder()
                .email(user.getEmail())
                .subject("Account verification")
                .message(userDto.getMessage())
                .build();

        mailService.sendEmail(mailStructure);

        return userVerificationDtoMapper.toUserVerificationDto2(userDao.updateUser(user), settings);
    }
    @Transactional
    @Override
    public UserVerificationDto rejectUserVerification(RejectUserDto rejectUserDto) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(rejectUserDto.getApplicantId());

        user.setVerified(false);
        user.setVerificationStatus(VerificationStatus.REJECTED);

        MailStructure mailStructure = MailStructure
                .builder()
                .email(user.getEmail())
                .subject("Account verification")
                .message(rejectUserDto.getMessage())
                .build();

        mailService.sendEmail(mailStructure);

        return userVerificationDtoMapper.toUserVerificationDto2(userDao.updateUser(user), settings);
    }

    @Override
    public List<UserVerificationDto> getVerificationRequests() {
        Settings settings = settingsDao.findBySettingsKey("domain");
        return userVerificationDao.getVerificationRequests().stream().map(userVerification -> userVerificationDtoMapper.toUserverificationDto(userVerification, settings)).collect(Collectors.toList());
    }


}
