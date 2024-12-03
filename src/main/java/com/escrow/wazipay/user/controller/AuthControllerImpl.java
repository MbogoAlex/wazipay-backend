package com.escrow.wazipay.user.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.security.JWTGenerator;
import com.escrow.wazipay.user.dto.UserLoginDto;
import com.escrow.wazipay.user.dto.UserRegistrationDto;
import com.escrow.wazipay.user.dto.UserSetPinDto;
import com.escrow.wazipay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthControllerImpl implements AuthController{
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JWTGenerator jwtGenerator;
    private final BuildResponse buildResponse = new BuildResponse();
    @Autowired
    public AuthControllerImpl(
            UserService userService,
            AuthenticationManager authenticationManager,
            JWTGenerator jwtGenerator
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }
    @PostMapping("register")
    @Override
    public ResponseEntity<Response> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        if(userService.existsByPhoneNumber(userRegistrationDto.getPhoneNumber()) || userService.existsByEmail(userRegistrationDto.getEmail())) {
            return buildResponse.createResponse(null, null, "User exists", HttpStatus.CONFLICT);
        }

        return buildResponse.createResponse("user", userService.createAccount(userRegistrationDto), "User registered", HttpStatus.CREATED);
    }
    @PutMapping("pin")
    @Override
    public ResponseEntity<Response> setPin(@RequestBody UserSetPinDto userSetPinDto) {
        if(userSetPinDto.getPin().length() < 6) {
            return buildResponse.createResponse(null, null, "Pin must be at least 6 digits", HttpStatus.FORBIDDEN);

        } else {
            return buildResponse.createResponse("user", userService.setUserPin(userSetPinDto), "New pin created", HttpStatus.OK);
        }
    }

    @PostMapping("login")
    @Override
    public ResponseEntity<Response> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getPhoneNumber(),
                            userLoginDto.getPin()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            Map<Object, Object> userMap = new HashMap<>();
            userMap.put("user", userService.getUserByPhoneNumber(userLoginDto.getPhoneNumber()));
            userMap.put("token", token);

            return buildResponse.createResponse("user", userMap, "user logged in", HttpStatus.OK);

        } catch (AuthenticationException e) {
            return buildResponse.createResponse(null, null, e.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
