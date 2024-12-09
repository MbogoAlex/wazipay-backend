package com.escrow.wazipay.business.service;

import com.escrow.wazipay.business.dao.BusinessDao;
import com.escrow.wazipay.business.dto.BusinessDto;
import com.escrow.wazipay.business.dto.CreateBusinessDto;
import com.escrow.wazipay.business.dto.UpdateBusinessDto;
import com.escrow.wazipay.business.dto.mapper.BusinessDtoMapper;
import com.escrow.wazipay.business.entity.Business;
import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService{
    private final BusinessDtoMapper businessDtoMapper = new BusinessDtoMapper();
    private final BusinessDao businessDao;
    private final UserDao userDao;
    @Autowired
    public BusinessServiceImpl(
            BusinessDao businessDao,
            UserDao userDao
    ) {
        this.businessDao = businessDao;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public BusinessDto addBusiness(CreateBusinessDto newBusiness) {
        User user = userDao.getUserByUserId(newBusiness.getUserId());

        Business business = Business.builder()
                .businessName(newBusiness.getBusinessName())
                .businessDescription(newBusiness.getBusinessDescription())
                .businessLocation(newBusiness.getBusinessLocation())
                .createdAt(LocalDateTime.now())
                .archived(false)
                .user(user)
                .build();

        return businessDtoMapper.toBusinessDto(businessDao.createBusiness(business));
    }
    @Transactional
    @Override
    public BusinessDto updateBusiness(UpdateBusinessDto updateBusinessDto) {
        Business business = businessDao.getBusinessById(updateBusinessDto.getBusinessId());

        if(!updateBusinessDto.getBusinessName().equals(business.getBusinessName())) {
            business.setBusinessName(updateBusinessDto.getBusinessName());
            business.setLastUpdate(LocalDateTime.now());
        }

        if(!updateBusinessDto.getBusinessDescription().equals(business.getBusinessDescription())) {
            business.setBusinessDescription(updateBusinessDto.getBusinessDescription());
            business.setLastUpdate(LocalDateTime.now());
        }

        if(!updateBusinessDto.getBusinessLocation().equals(business.getBusinessLocation())) {
            business.setBusinessLocation(updateBusinessDto.getBusinessLocation());
            business.setLastUpdate(LocalDateTime.now());
        }

        return businessDtoMapper.toBusinessDto(businessDao.updateBusiness(business));
    }

    @Override
    public BusinessDto getBusinessById(Integer id) {
        return businessDtoMapper.toBusinessDto(businessDao.getBusinessById(id));
    }

    @Override
    public List<BusinessDto> getUserBusinesses(Integer userId) {
        return businessDao.getUserBusinesses(userId).stream().map(businessDtoMapper::toBusinessDto).collect(Collectors.toList());
    }
}
