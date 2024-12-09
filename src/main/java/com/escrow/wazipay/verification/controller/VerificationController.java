package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.verification.dto.ApproveUserDto;
import com.escrow.wazipay.verification.dto.RejectUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationController {
    ResponseEntity<Response> uploadUserVerificationDetails(Integer userId, MultipartFile[] images);
    ResponseEntity<Response> getUserVerificationDetails(Integer userId);

    ResponseEntity<Response> approveUser(ApproveUserDto approveUserDto);
    ResponseEntity<Response> rejectUserVerification(RejectUserDto rejectUserDto);
    ResponseEntity<Response> getVerificationRequests();
}
