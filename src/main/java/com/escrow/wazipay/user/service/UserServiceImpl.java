package com.escrow.wazipay.user.service;

import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.dto.*;
import com.escrow.wazipay.user.dto.mapper.UserDtoMapper;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.user.entity.UserRole;
import com.escrow.wazipay.user.entity.UserRoleEnum;
import com.escrow.wazipay.user.entity.VerificationStatus;
import com.escrow.wazipay.verification.dto.ApproveUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public UserDto createAccount(UserRegistrationDto userRegistrationDto) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        UserAccount user = UserAccount.builder()
                .name(userRegistrationDto.getName())
                .email(userRegistrationDto.getEmail())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .verified(false)
                .verificationStatus(VerificationStatus.NOT_VERIFIED)
                .createdAt(LocalDateTime.now())
                .archived(false)
                .suspended(false)
                .suspensions(new ArrayList<>())
                .build();

        userRole.setUser(user);
        userRole.setRole(UserRoleEnum.BUYER);
        userRoles.add(userRole);
        user.setRoles(userRoles);

        return userDtoMapper.toUserDto(userDao.createAccount(user), domain);
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        return null;
    }
    @Transactional
    @Override
    public UserDto updateUserGeneralDetails(UserUpdateDto userUpdateDto) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(userUpdateDto.getUserId());
        if(!user.getName().equalsIgnoreCase(userUpdateDto.getName())) {
            user.setName(userUpdateDto.getName());
        }
        if(!user.getPhoneNumber().equalsIgnoreCase(userUpdateDto.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }
        if(!user.getEmail().equalsIgnoreCase(userUpdateDto.getEmail())) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if(user.getVerificationStatus() != VerificationStatus.valueOf(userUpdateDto.getVerificationStatus().toUpperCase())) {
            user.setVerificationStatus(VerificationStatus.valueOf(userUpdateDto.getVerificationStatus().toUpperCase()));
        }
        return userDtoMapper.toUserDto(userDao.updateUser(user), domain);
    }
    @Transactional
    @Override
    public UserDto setUserPin(UserSetPinDto userSetPinDto) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(userSetPinDto.getUserId());
        user.setPin(passwordEncoder.encode(userSetPinDto.getPin()));
        return userDtoMapper.toUserDto(userDao.updateUser(user), domain);
    }

    @Override
    public UserDto getUserByUserId(Integer userId) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        return userDtoMapper.toUserDto(userDao.getUserByUserId(userId), domain);
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        return userDtoMapper.toUserDto(userDao.getUserByPhoneNumber(phoneNumber), domain);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        return userDtoMapper.toUserDto(userDao.getUserByEmail(email), domain);
    }
    @Transactional
    @Override
    public UserDto approveUser(ApproveUserDto userDto) {
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
        return userDtoMapper.toUserDto(userDao.updateUser(user), settings);
    }
    @Transactional
    @Override
    public UserDto disapproveUser(Integer userId) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(userId);

        user.setVerified(false);
        user.setVerificationStatus(VerificationStatus.REJECTED);

        return userDtoMapper.toUserDto(userDao.updateUser(user), settings);
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userDao.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public List<UserDto> getUsers() {
        Settings domain = settingsDao.findBySettingsKey("domain");
        return userDao.getAllUsers().stream()
                .map(user -> userDtoMapper.toUserDto(user, domain))
                .collect(Collectors.toList());
    }
}
