package com.escrow.wazipay.service.user;

import com.escrow.wazipay.model.user.UserDetailsResponseDto;
import com.escrow.wazipay.model.user.UserRegistrationRequestDto;
import com.escrow.wazipay.model.user.UserSetPinRequestDto;
import com.escrow.wazipay.model.user.UserVerificationDetailsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserDetailsResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto);
    UserDetailsResponseDto setUserPin(UserSetPinRequestDto userSetPinRequestDto);
    UserDetailsResponseDto getUserById(Integer userId);
    UserDetailsResponseDto getUserByEmail(String email);
    UserDetailsResponseDto getUserByPhoneNumber(String phoneNumber);
    UserVerificationDetailsResponseDto requestVerification(Integer userId, MultipartFile[] files) throws IOException;

    Boolean userExists(String phoneNumber, String email);
}
