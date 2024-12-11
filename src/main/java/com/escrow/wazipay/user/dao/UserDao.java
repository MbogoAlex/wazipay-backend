package com.escrow.wazipay.user.dao;

import com.escrow.wazipay.user.entity.UserAccount;
import java.util.List;


public interface UserDao {
    UserAccount createAccount(UserAccount user);
    UserAccount updateUser(UserAccount user);
    UserAccount getUserByUserId(Integer userId);
    UserAccount getUserByPhoneNumber(String phoneNumber);
    UserAccount getUserByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
    List<UserAccount> getAllUsers();
}
