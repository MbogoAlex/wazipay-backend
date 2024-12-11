package com.escrow.wazipay.wazipayWallet.dto.mapper;

import com.escrow.wazipay.media.entity.Settings;
import com.escrow.wazipay.user.dto.mapper.UserDtoMapper;
import com.escrow.wazipay.wazipayWallet.dto.WazipayTransactionDto;
import com.escrow.wazipay.wazipayWallet.dto.WazipayWalletDto;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWallet;
import com.escrow.wazipay.wazipayWallet.entity.WazipayWalletTransaction;

public class WazipayWalletTransactionMapper {

    UserDtoMapper userDtoMapper = new UserDtoMapper();
    public WazipayWalletDto toWalletDto(WazipayWallet wallet) {
        return WazipayWalletDto.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }

    public WazipayTransactionDto toWazipayTransactionDto(WazipayWalletTransaction wazipayWalletTransaction, Settings settings) {
        return WazipayTransactionDto.builder()
                .id(wazipayWalletTransaction.getId())
                .transactionType(wazipayWalletTransaction.getWazipayWalletTransactionType())
                .transactionCode(wazipayWalletTransaction.getTransactionCode())
                .amount(wazipayWalletTransaction.getTransactionAmount())
                .createdAt(wazipayWalletTransaction.getCreatedAt())
                .user(userDtoMapper.toUserDto(wazipayWalletTransaction.getUser(), settings))
                .build();
    }
}
