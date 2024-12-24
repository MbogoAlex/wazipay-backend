package com.escrow.wazipay.dao.user;

import com.escrow.wazipay.model.user.UserAccount;
import com.escrow.wazipay.model.user.UserVerification;
import com.escrow.wazipay.model.user.VerificationFile;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDao {
    UserAccount createUserAccount(UserAccount userAccount);
    UserAccount updateUser(UserAccount userAccount);

    UserAccount getUserById(Integer userId);
    UserAccount getUserByEmail(String email);
    UserAccount getUserByPhoneNumber(String phoneNumber);
    Boolean userExists(String phoneNumber, String email);

    VerificationFile uploadFile(VerificationFile verificationFile);
    VerificationFile updateFile(VerificationFile verificationFile);

    VerificationFile getFileById(Integer fileId);
    List<VerificationFile> getUserVerificationFiles(Integer userId);
    String deleteFile(Integer fileId);

    UserVerification createUserVerification(UserVerification userVerification);
    UserVerification updateUserVerification(UserVerification userVerification);
    UserVerification getUserVerificationDetails(Integer userId);
    UserVerification getVerificationDetailsById(Integer id);
    List<UserVerification> getAllVerificationDetails();
    List<UserVerification> getVerificationDetails(Integer userId, String status, LocalDateTime startDate, LocalDateTime endDate);
}
