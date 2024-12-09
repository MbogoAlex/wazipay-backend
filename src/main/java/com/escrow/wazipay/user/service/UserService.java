package com.escrow.wazipay.user.service;

import com.escrow.wazipay.user.dto.*;
import com.escrow.wazipay.verification.dto.ApproveUserDto;

import java.util.List;

public interface UserService {
    UserDto createAccount(UserRegistrationDto userRegistrationDto);
    UserDto login(UserLoginDto userLoginDto);
    UserDto updateUserGeneralDetails(UserUpdateDto user);
    UserDto setUserPin(UserSetPinDto user);
    UserDto getUserByUserId(Integer userId);
    UserDto getUserByPhoneNumber(String phoneNumber);
    UserDto getUserByEmail(String email);

    UserDto approveUser(ApproveUserDto approveUserDto);
    UserDto disapproveUser(Integer userId);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
    List<UserDto> getUsers();

}
