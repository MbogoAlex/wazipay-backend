package com.escrow.wazipay.userWallet.controller;

import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;

public interface UserWalletController {
    ResponseEntity<Response> getUserWalletDetails(Integer userId);
}
