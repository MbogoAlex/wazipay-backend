package com.escrow.wazipay.verification.service;

import com.escrow.wazipay.verification.dto.UserVerificationDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserVerificationService {
    UserVerificationDto uploadUserVerificationDetails(Integer userId, MultipartFile[] images) throws IOException;

    UserVerificationDto getUserVerificationDetails(Integer userId);
}
