package com.escrow.wazipay.verification.dao;

import com.escrow.wazipay.verification.entity.UserVerification;

public interface UserVerificationDao {
    UserVerification uploadUserVerificationDetails(UserVerification userVerification);
}
