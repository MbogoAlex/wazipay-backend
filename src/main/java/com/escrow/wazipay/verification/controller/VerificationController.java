package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationController {
    ResponseEntity<Response> uploadUserVerificationDetails(Integer userId, MultipartFile[] images);
    ResponseEntity<Response> getUserVerificationDetails(Integer userId);
}
