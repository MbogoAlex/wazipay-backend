package com.escrow.wazipay.verification.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.service.UserService;
import com.escrow.wazipay.verification.dto.ApproveUserDto;
import com.escrow.wazipay.verification.dto.RejectUserDto;
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
    private final UserService userService;
    @Autowired
    public VerificationControllerImpl(
            UserVerificationService userVerificationService,
            UserService userService
    ) {
        this.userVerificationService = userVerificationService;
        this.userService = userService;
    }
    @PostMapping("verificationrequest/{userId}")
    @Override
    public ResponseEntity<Response> uploadUserVerificationDetails(
            @PathVariable("userId") Integer userId,
            @RequestPart("file") MultipartFile[] images
    ) {
        if(images != null && images.length > 1) {
            try {
               return buildResponse.createResponse("user", userVerificationService.uploadUserVerificationDetails(userId, images), "Verification details uploaded", HttpStatus.CREATED);
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
    @PutMapping("verification/approve")
    @Override
    public ResponseEntity<Response> approveUser(@RequestBody ApproveUserDto approveUserDto) {
        UserDto user = userService.getUserByUserId(approveUserDto.getAdminId());
        if(user.getRoles().contains("ADMIN")) {
            return buildResponse.createResponse("verification", userVerificationService.approveUser(approveUserDto), "User approved", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("verification", "Not allowed to do this action", "FAIL", HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("verification/reject")
    @Override
    public ResponseEntity<Response> rejectUserVerification(@RequestBody RejectUserDto rejectUserDto) {
        UserDto user = userService.getUserByUserId(rejectUserDto.getAdminId());
        if(user.getRoles().contains("ADMIN")) {
            return buildResponse.createResponse("verification", userVerificationService.rejectUserVerification(rejectUserDto), "User rejected", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("verification", "Not allowed to do this action", "User not approved", HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("verification/requests")
    @Override
    public ResponseEntity<Response> getVerificationRequests() {
        return buildResponse.createResponse("verification", userVerificationService.getVerificationRequests(), "Verification requests fetched", HttpStatus.OK);
    }
}
