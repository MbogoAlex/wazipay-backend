package com.escrow.wazipay.wazipayWallet.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.wazipayWallet.service.WazipayWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class WazipayWalletControllerImpl implements WazipayWalletController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final WazipayWalletService wazipayWalletService;
    @Autowired
    public WazipayWalletControllerImpl(
            WazipayWalletService wazipayWalletService
    ) {
        this.wazipayWalletService = wazipayWalletService;
    }
    @GetMapping("wazipaywallet")
    @Override
    public ResponseEntity<Response> getWazipayWalletDetails() {
        return buildResponse.createResponse("Wazipay wallet", wazipayWalletService.getWallets().get(0), "Wazipay wallet fetched", HttpStatus.OKgit add);
    }
}
