package com.escrow.wazipay.verification.dao;

import com.escrow.wazipay.verification.entity.UserVerification;
import java.util.List;

public interface UserVerificationDao {
    UserVerification uploadUserVerificationDetails(UserVerification userVerification);

    UserVerification getUserVerificationDetails(Integer userId);

    List<UserVerification> getVerificationRequests();
}
