package com.escrow.wazipay.userWallet.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.userWallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserWalletControllerImpl implements UserWalletController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final UserWalletService userWalletService;
    @Autowired
    public UserWalletControllerImpl(
            UserWalletService userWalletService
    ) {
        this.userWalletService = userWalletService;
    }
    @GetMapping("userwallet/{userId}")
    @Override
    public ResponseEntity<Response> getUserWalletDetails(
            @PathVariable("userId") Integer userId
    ) {
        return buildResponse.createResponse("user wallet", userWalletService.getUserWalletDetails(userId), "User wallet fetched", HttpStatus.OK);
    }
}
