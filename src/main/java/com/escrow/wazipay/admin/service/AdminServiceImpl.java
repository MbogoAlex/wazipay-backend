package com.escrow.wazipay.admin.service;

import com.escrow.wazipay.media.dao.SettingsDao;
import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.dto.mapper.UserDtoMapper;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.user.entity.UserRole;
import com.escrow.wazipay.user.entity.UserRoleEnum;
import com.escrow.wazipay.user.entity.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();
    private final UserDao userDao;
    private final SettingsDao settingsDao;
    @Autowired
    public AdminServiceImpl(
            UserDao userDao,
            SettingsDao settingsDao
    ) {
        this.userDao = userDao;
        this.settingsDao = settingsDao;
    }
    @Transactional
    @Override
    public UserDto setAdmin(Integer userId) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        UserAccount user = userDao.getUserByUserId(userId);

        user.setVerified(true);
        user.setVerificationStatus(VerificationStatus.VERIFIED);


        UserRole userRole = UserRole.builder()
                .user(user)
                .role(UserRoleEnum.ADMIN)
                .build();

        user.getRoles().add(userRole);
        user.setVerifiedAt(LocalDateTime.now());

        return userDtoMapper.toUserDto(userDao.updateUser(user), settings);
    }
}
