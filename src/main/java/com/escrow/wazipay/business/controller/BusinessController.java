package com.escrow.wazipay.business.controller;

import com.escrow.wazipay.business.dto.CreateBusinessDto;
import com.escrow.wazipay.business.dto.UpdateBusinessDto;
import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface BusinessController {
    ResponseEntity<Response> addBusiness(CreateBusinessDto newBusiness, User user);
    ResponseEntity<Response>  updateBusiness(UpdateBusinessDto updateBusinessDto);
    ResponseEntity<Response>  getBusinessById(Integer id);
    ResponseEntity<Response>  getUserBusinesses(Integer userId);
}
