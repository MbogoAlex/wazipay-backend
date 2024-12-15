package com.escrow.wazipay.userWallet.service;

import com.escrow.wazipay.userWallet.dao.UserWalletDao;
import com.escrow.wazipay.userWallet.dto.UserWalletDto;
import com.escrow.wazipay.userWallet.dto.mapper.UserWalletDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService{
    private final UserWalletDtoMapper userWalletDtoMapper = new UserWalletDtoMapper();
    private final UserWalletDao userWalletDao;
    @Autowired
    public UserWalletServiceImpl(
            UserWalletDao userWalletDao
    ) {
        this.userWalletDao = userWalletDao;
    }

    @Override
    public UserWalletDto getUserWalletDetails(Integer userId) {
        return userWalletDtoMapper.toUserWalletDto(userWalletDao.getUserWallet(userId));
    }
}
