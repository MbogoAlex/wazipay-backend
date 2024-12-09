package com.escrow.wazipay.verification.service;

import com.escrow.wazipay.verification.dto.ApproveUserDto;
import com.escrow.wazipay.verification.dto.RejectUserDto;
import com.escrow.wazipay.verification.dto.UserVerificationDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserVerificationService {
    UserVerificationDto uploadUserVerificationDetails(Integer userId, MultipartFile[] images) throws IOException;

    UserVerificationDto getUserVerificationDetails(Integer userId);

    UserVerificationDto approveUser(ApproveUserDto approveUserDto);
    UserVerificationDto rejectUserVerification(RejectUserDto rejectUserDto);

    List<UserVerificationDto> getVerificationRequests();
}
