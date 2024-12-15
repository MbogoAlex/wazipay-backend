package com.escrow.wazipay.userWallet.service;

import com.escrow.wazipay.userWallet.dto.UserWalletDto;

public interface UserWalletService {
    UserWalletDto getUserWalletDetails(Integer userId);
}
