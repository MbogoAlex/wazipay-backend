package com.escrow.wazipay.admin.service;

import com.escrow.wazipay.user.dto.UserDto;

public interface AdminService {
    UserDto setAdmin(Integer userId);
}
