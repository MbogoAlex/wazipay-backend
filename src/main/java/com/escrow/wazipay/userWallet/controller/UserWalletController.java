package com.escrow.wazipay.userWallet.controller;

import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface UserWalletController {
    ResponseEntity<Response> getUserWalletDetails(User user);
}
