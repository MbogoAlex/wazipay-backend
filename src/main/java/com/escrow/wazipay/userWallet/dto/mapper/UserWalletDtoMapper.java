package com.escrow.wazipay.userWallet.dto.mapper;

import com.escrow.wazipay.userWallet.dto.UserWalletDto;
import com.escrow.wazipay.userWallet.entity.UserWallet;

public class UserWalletDtoMapper {
    public UserWalletDto toUserWalletDto(UserWallet userWallet) {
        return UserWalletDto.builder()
                .walletId(userWallet.getId())
                .userId(userWallet.getUser().getId())
                .username(userWallet.getUser().getName())
                .walletBalance(userWallet.getBalance())
                .build();
    }
}
