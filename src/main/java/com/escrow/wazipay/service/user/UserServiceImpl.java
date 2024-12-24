package com.escrow.wazipay.service.user;

import com.escrow.wazipay.dao.settings.SettingsDao;
import com.escrow.wazipay.dao.user.UserDao;
import com.escrow.wazipay.model.settings.Settings;
import com.escrow.wazipay.model.user.*;
import com.escrow.wazipay.service.user.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();
    private final UserDao userDao;
    private final SettingsDao settingsDao;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(
            UserDao userDao,
            SettingsDao settingsDao,
            PasswordEncoder passwordEncoder
    ) {
        this.userDao = userDao;
        this.settingsDao = settingsDao;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public UserDetailsResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.BUYER);

        UserAccount userAccount = UserAccount.builder()
                .username(userRegistrationRequestDto.getUsername())
                .phoneNumber(userRegistrationRequestDto.getPhoneNumber())
                .email(userRegistrationRequestDto.getEmail())
                .createdAt(LocalDateTime.now())
                .verified(false)
                .archived(false)
                .roles(roles)
                .build();

        return userDtoMapper.userDetailsResponseDto(userDao.createUserAccount(userAccount), settings.getValue());
    }
    @Transactional
    @Override
    public UserDetailsResponseDto setUserPin(UserSetPinRequestDto userSetPinRequestDto) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        UserAccount userAccount = userDao.getUserById(userSetPinRequestDto.getUserId());
        userAccount.setPin(passwordEncoder.encode(userSetPinRequestDto.getPin()));

        return userDtoMapper.userDetailsResponseDto(userDao.updateUser(userAccount), settings.getValue());
    }

    @Override
    public UserDetailsResponseDto getUserById(Integer userId) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        return userDtoMapper.userDetailsResponseDto(userDao.getUserById(userId), settings.getValue());
    }

    @Override
    public UserDetailsResponseDto getUserByEmail(String email) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        return userDtoMapper.userDetailsResponseDto(userDao.getUserByEmail(email), settings.getValue());
    }

    @Override
    public UserDetailsResponseDto getUserByPhoneNumber(String phoneNumber) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        return userDtoMapper.userDetailsResponseDto(userDao.getUserByPhoneNumber(phoneNumber), settings.getValue());
    }
    @Transactional
    @Override
    public UserVerificationDetailsResponseDto requestVerification(Integer userId, MultipartFile[] files) throws IOException {

        List<VerificationFile> idImages = new ArrayList<>();
        System.out.println("GETTING USER");
        System.out.println("ARRAY LENGTH: "+ files.length);
        UserAccount user = userDao.getUserById(userId);
        String basePath = settingsDao.findBySettingsKey("imagePath").getValue();
        Settings domain = settingsDao.findBySettingsKey("domain");

        MultipartFile idFront = files[0];
        MultipartFile idBack = files[1];

        // Upload idFront
        String idF_fileName = System.currentTimeMillis() + "_" + idFront.getOriginalFilename().replace(" ", "");
        String idF_filePath = basePath + idF_fileName;
        File idF_uploadFile = new File(idF_filePath);
        idFront.transferTo(idF_uploadFile);

        VerificationFile frontId = VerificationFile.builder()
                .name(idF_fileName)
                .user(user) // Link to the user
                .build();

        // Upload idBack
        String idB_fileName = System.currentTimeMillis() + "_" + idBack.getOriginalFilename().replace(" ", "");
        String idB_filePath = basePath + idB_fileName;
        File idB_uploadFile = new File(idB_filePath);
        idBack.transferTo(idB_uploadFile);

        VerificationFile backId = VerificationFile.builder()
                .name(idB_fileName)
                .user(user) // Link to the user
                .build();

        // Build and link UserVerification object
        UserVerification userVerification = UserVerification.builder()
                .userAccount(user) // Important: Set the user here
                .verificationStatus(VerificationStatus.PENDING_VERIFICATION)
                .build();

        // Set userVerification for each image
        frontId.setUserVerification(userVerification);
        backId.setUserVerification(userVerification);

        idImages.add(frontId);
        idImages.add(backId);

        // Set the images in the UserVerification object
        userVerification.setFiles(idImages);

        // Link verification object to the user
        user.setUserVerification(userVerification);

        return userDtoMapper.userVerificationDetailsDto(userDao.createUserVerification(userVerification), domain.getValue());
    }

    @Override
    public Boolean userExists(String phoneNumber, String email) {
        return userDao.userExists(phoneNumber, email);
    }
}
