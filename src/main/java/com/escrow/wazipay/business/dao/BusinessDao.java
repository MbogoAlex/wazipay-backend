package com.escrow.wazipay.business.dao;

import com.escrow.wazipay.business.entity.Business;
import java.util.List;

public interface BusinessDao {
    Business createBusiness(Business business);
    Business updateBusiness(Business business);
    Business getBusinessById(Integer businessId);
    List<Business> getUserBusinesses(Integer userId);
}
