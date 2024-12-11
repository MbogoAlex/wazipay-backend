package com.escrow.wazipay.business.service;

import com.escrow.wazipay.business.dto.BusinessDto;
import com.escrow.wazipay.business.dto.CreateBusinessDto;
import com.escrow.wazipay.business.dto.UpdateBusinessDto;

import java.util.List;

public interface BusinessService {
    BusinessDto addBusiness(CreateBusinessDto newBusiness, Integer userId);
    BusinessDto updateBusiness(UpdateBusinessDto updateBusinessDto);
    BusinessDto getBusinessById(Integer id);
    List<BusinessDto> getUserBusinesses(Integer userId);
}
