package com.escrow.wazipay.service.admin;

import com.escrow.wazipay.dao.settings.SettingsDao;
import com.escrow.wazipay.dao.user.UserDao;
import com.escrow.wazipay.model.settings.Settings;
import com.escrow.wazipay.model.user.UserAccount;
import com.escrow.wazipay.model.user.UserVerification;
import com.escrow.wazipay.model.user.UserVerificationDetailsResponseDto;
import com.escrow.wazipay.model.user.VerificationStatus;
import com.escrow.wazipay.service.user.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
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

    @Override
    public List<UserVerificationDetailsResponseDto> getVerificationRequests() {

        Settings settings = settingsDao.findBySettingsKey("domain");

        List<UserVerification> userVerifications = userDao.getVerificationDetails(null, String.valueOf(VerificationStatus.PENDING_VERIFICATION), null, null);

        return userVerifications.stream().map(userVerification -> userDtoMapper.userVerificationDetailsDto(userVerification, settings.getValue())).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public UserVerificationDetailsResponseDto verifyUser(Integer userId) {

        Settings settings = settingsDao.findBySettingsKey("domain");

        UserAccount userAccount = userDao.getUserById(userId);

        UserVerification userVerification = userAccount.getUserVerification();
        userVerification.setVerificationStatus(VerificationStatus.VERIFIED);
        userVerification.setVerifiedAt(LocalDateTime.now());
        userVerification.getUserAccount().setVerified(true);


        return userDtoMapper.userVerificationDetailsDto(userDao.updateUserVerification(userVerification), settings.getValue());
    }
}
