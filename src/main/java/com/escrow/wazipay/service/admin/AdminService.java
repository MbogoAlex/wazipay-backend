package com.escrow.wazipay.service.admin;
import com.escrow.wazipay.model.user.UserVerificationDetailsResponseDto;

import java.util.List;

public interface AdminService {
    List<UserVerificationDetailsResponseDto> getVerificationRequests();
    UserVerificationDetailsResponseDto verifyUser(Integer userId);
}
