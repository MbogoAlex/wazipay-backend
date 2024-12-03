package com.escrow.wazipay.user.service;

import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.dto.*;
import com.escrow.wazipay.user.dto.mapper.UserDtoMapper;
import com.escrow.wazipay.user.entity.User;
import com.escrow.wazipay.user.entity.UserRole;
import com.escrow.wazipay.user.entity.UserRoleEnum;
import com.escrow.wazipay.user.entity.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(
            UserDao userDao,
            PasswordEncoder passwordEncoder
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public UserDto createAccount(UserRegistrationDto userRegistrationDto) {
        UserRole userRole = new UserRole();
        User user = User.builder()
                .name(userRegistrationDto.getName())
                .email(userRegistrationDto.getEmail())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .verificationStatus(VerificationStatus.PENDING_VERIFICATION)
                .createdAt(LocalDateTime.now())
                .archived(false)
                .build();

        userRole.setUser(user);
        userRole.setRole(UserRoleEnum.valueOf(userRegistrationDto.getRole().toUpperCase()));

        user.getRoles().add(userRole);

        return userDtoMapper.toUserDto(userDao.createAccount(user));
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        return null;
    }
    @Transactional
    @Override
    public UserDto updateUserGeneralDetails(UserUpdateDto userUpdateDto) {
        User user = userDao.getUserByUserId(userUpdateDto.getUserId());
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
        return userDtoMapper.toUserDto(userDao.updateUser(user));
    }
    @Transactional
    @Override
    public UserDto updateUserPassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        User user = userDao.getUserByUserId(userUpdatePasswordDto.getUserId());
        user.setPassword(passwordEncoder.encode(userUpdatePasswordDto.getNewPassword()));
        return userDtoMapper.toUserDto(userDao.updateUser(user));
    }

    @Override
    public UserDto getUserByUserId(Integer userId) {
        return userDtoMapper.toUserDto(userDao.getUserByUserId(userId));
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userDtoMapper.toUserDto(userDao.getUserByPhoneNumber(phoneNumber));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userDtoMapper.toUserDto(userDao.getUserByEmail(email));
    }

    @Override
    public List<UserDto> getUsers() {
        return userDao.getAllUsers().stream().map(user -> userDtoMapper.toUserDto(user)).collect(Collectors.toList());
    }
}
