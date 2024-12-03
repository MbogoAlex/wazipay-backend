package com.escrow.wazipay.user.dao;

import com.escrow.wazipay.user.entity.User;
import java.util.List;


public interface UserDao {
    User createAccount(User user);
    User updateUser(User user);
    User getUserByUserId(Integer userId);
    User getUserByPhoneNumber(String phoneNumber);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
