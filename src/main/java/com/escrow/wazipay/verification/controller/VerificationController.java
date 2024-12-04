package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.verification.dto.VerifyUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationController {
    ResponseEntity<Response> uploadUserVerificationDetails(VerifyUserDto verifyUserDto, MultipartFile[] images);
}
