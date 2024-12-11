package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.verification.dto.ApproveUserDto;
import com.escrow.wazipay.verification.dto.RejectUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationController {
    ResponseEntity<Response> uploadUserVerificationDetails(Integer userId, MultipartFile[] images);
    ResponseEntity<Response> getUserVerificationDetails(Integer userId, User user);

    ResponseEntity<Response> approveUser(ApproveUserDto approveUserDto, User user);
    ResponseEntity<Response> rejectUserVerification(
            RejectUserDto rejectUserDto,
            User user
    );
    ResponseEntity<Response> getVerificationRequests();
}
