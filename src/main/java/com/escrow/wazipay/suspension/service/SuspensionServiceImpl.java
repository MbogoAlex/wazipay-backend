package com.escrow.wazipay.suspension.service;

import com.escrow.wazipay.mail.MailService;
import com.escrow.wazipay.mail.MailStructure;
import com.escrow.wazipay.suspension.dao.SuspensionDao;
import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspensionReasonUpdateDto;
import com.escrow.wazipay.suspension.dto.mapper.SuspensionMapperDto;
import com.escrow.wazipay.suspension.entity.Suspension;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuspensionServiceImpl implements SuspensionService{
    private final SuspensionMapperDto suspensionMapperDto = new SuspensionMapperDto();
    private final SuspensionDao suspensionDao;
    private final UserDao userDao;

    private final MailService mailService;
    @Autowired
    public SuspensionServiceImpl(
            SuspensionDao suspensionDao,
            UserDao userDao,
            MailService mailService
    ) {
        this.suspensionDao = suspensionDao;
        this.userDao = userDao;
        this.mailService = mailService;
    }
    @Transactional
    @Override
    public SuspensionDto suspendUser(SuspendUserDto suspendUserDto) {
        User user = userDao.getUserByUserId(suspendUserDto.getUserId());

        user.setSuspended(true);

        Suspension suspension = Suspension.builder()
                .suspendedAt(LocalDateTime.now())
                .suspensionReason(suspendUserDto.getSuspensionReason())
                .suspensionLifted(false)
                .user(user)
                .build();

        MailStructure mailStructure = MailStructure
                .builder()
                .email(user.getEmail())
                .subject("Account Suspension")
                .message(suspendUserDto.getSuspensionReason())
                .build();

        mailService.sendEmail(mailStructure);

        return suspensionMapperDto.toSuspensionDto(suspensionDao.suspendUser(suspension));
    }
    @Transactional
    @Override
    public SuspensionDto updateSuspensionReason(SuspensionReasonUpdateDto suspensionDto) {
        Suspension suspension = suspensionDao.getSuspension(suspensionDto.getSuspensionId());
        suspension.setSuspensionReason(suspensionDto.getSuspensionReason());

        return suspensionMapperDto.toSuspensionDto(suspensionDao.updateSuspension(suspension));
    }
    @Transactional
    @Override
    public SuspensionDto updateSuspensionLiftReason(LiftSuspensionDto liftSuspensionDto) {
        Suspension suspension = suspensionDao.getSuspension(liftSuspensionDto.getSuspensionId());
        suspension.setSuspensionLiftReason(liftSuspensionDto.getSuspensionLiftReason());

        return suspensionMapperDto.toSuspensionDto(suspensionDao.updateSuspension(suspension));
    }
    @Transactional
    @Override
    public SuspensionDto liftSuspension(LiftSuspensionDto liftSuspensionDto) {
        Suspension suspension = suspensionDao.getSuspension(liftSuspensionDto.getSuspensionId());
        suspension.setSuspensionLiftReason(liftSuspensionDto.getSuspensionLiftReason());
        suspension.setSuspensionLifted(true);
        suspension.setSuspensionLiftedAt(LocalDateTime.now());

        User user = suspension.getUser();
        user.setSuspended(false);
        suspension.setUser(user);

        MailStructure mailStructure = MailStructure
                .builder()
                .email("gitaumbogo3@gmail.com")
                .subject("Account Suspension")
                .message(liftSuspensionDto.getSuspensionLiftReason())
                .build();

        mailService.sendEmail(mailStructure);

        return suspensionMapperDto.toSuspensionDto(suspensionDao.updateSuspension(suspension));
    }

    @Override
    public SuspensionDto getSuspension(Integer id) {
        return suspensionMapperDto.toSuspensionDto(suspensionDao.getSuspension(id));
    }

    @Override
    public List<SuspensionDto> getUserSuspensions(Integer userId) {
        return suspensionDao.getUserSuspensions(userId).stream().map(suspensionMapperDto::toSuspensionDto).collect(Collectors.toList());
    }

    @Override
    public List<SuspensionDto> getAllSuspensions() {
        return suspensionDao.getAllSuspensions().stream().map(suspensionMapperDto::toSuspensionDto).collect(Collectors.toList());
    }
}
