package com.escrow.wazipay.wazipayWallet.controller;

import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;

public interface WazipayWalletController {
    ResponseEntity<Response> getWazipayWalletDetails();
}
