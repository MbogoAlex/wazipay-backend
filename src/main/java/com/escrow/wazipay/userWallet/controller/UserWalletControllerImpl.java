package com.escrow.wazipay.userWallet.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.service.UserService;
import com.escrow.wazipay.userWallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserWalletControllerImpl implements UserWalletController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final UserWalletService userWalletService;
    private final UserService userService;
    @Autowired
    public UserWalletControllerImpl(
            UserWalletService userWalletService,
            UserService userService
    ) {
        this.userWalletService = userWalletService;
        this.userService = userService;
    }
    @GetMapping("userwallet")
    @Override
    public ResponseEntity<Response> getUserWalletDetails(
            @AuthenticationPrincipal User user
    ) {
        UserDto userDto = userService.getUserByPhoneNumber(user.getUsername());
        return buildResponse.createResponse("user wallet", userWalletService.getUserWalletDetails(userDto.getUserId()), "User wallet fetched", HttpStatus.OK);
    }
}
