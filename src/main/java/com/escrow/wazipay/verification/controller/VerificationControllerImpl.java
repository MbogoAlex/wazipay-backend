package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.verification.dto.VerifyUserDto;
import com.escrow.wazipay.verification.service.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class VerificationControllerImpl implements VerificationController{

    private final BuildResponse buildResponse = new BuildResponse();
    private final UserVerificationService userVerificationService;
    @Autowired
    public VerificationControllerImpl(UserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }
    @PostMapping("verificationrequest")
    @Override
    public ResponseEntity<Response> uploadUserVerificationDetails(
            @RequestPart("data") VerifyUserDto verifyUserDto,
            @RequestPart("file") MultipartFile[] images
    ) {
        if(images != null && images.length > 1) {
            try {
               return buildResponse.createResponse("user", userVerificationService.uploadUserVerificationDetails(verifyUserDto, images), "Verification details uploaded", HttpStatus.CREATED);
            } catch (Exception e) {
                return buildResponse.createResponse(null, null, e.toString(), HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return buildResponse.createResponse(null, null, "Upload required documents", HttpStatus.LENGTH_REQUIRED);
        }
    }
    @GetMapping("verificationstatus/{userId}")
    @Override
    public ResponseEntity<Response> getUserVerificationDetails(@PathVariable(name = "userId") Integer userId) {
        return buildResponse.createResponse("user", userVerificationService.getUserVerificationDetails(userId), "Verification details fetched", HttpStatus.OK);
    }
}
