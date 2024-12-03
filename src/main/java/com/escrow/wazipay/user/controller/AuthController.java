package com.escrow.wazipay.user.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.user.dto.UserLoginDto;
import com.escrow.wazipay.user.dto.UserRegistrationDto;
import com.escrow.wazipay.user.dto.UserSetPinDto;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<Response> register(UserRegistrationDto userRegistrationDto);
    ResponseEntity<Response> setPin(UserSetPinDto userSetPinDto);
    ResponseEntity<Response> login(UserLoginDto userLoginDto);
}
