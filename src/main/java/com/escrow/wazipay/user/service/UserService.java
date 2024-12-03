package com.escrow.wazipay.user.service;

import com.escrow.wazipay.user.dto.*;
import java.util.List;

public interface UserService {
    UserDto createAccount(UserRegistrationDto userRegistrationDto);
    UserDto login(UserLoginDto userLoginDto);
    UserDto updateUserGeneralDetails(UserUpdateDto user);
    UserDto updateUserPassword(UserUpdatePasswordDto user);
    UserDto getUserByUserId(Integer userId);
    UserDto getUserByPhoneNumber(String phoneNumber);
    UserDto getUserByEmail(String email);
    List<UserDto> getUsers();

}
