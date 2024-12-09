package com.escrow.wazipay.business.controller;

import com.escrow.wazipay.business.dto.CreateBusinessDto;
import com.escrow.wazipay.business.dto.UpdateBusinessDto;
import com.escrow.wazipay.business.service.BusinessService;
import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class BusinessControllerImpl implements BusinessController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final BusinessService businessService;
    private final UserService userService;
    @Autowired
    public BusinessControllerImpl(
            BusinessService businessService,
            UserService userService
    ) {
        this.businessService = businessService;
        this.userService = userService;
    }
    @PostMapping("business")
    @Override
    public ResponseEntity<Response> addBusiness(
            @RequestBody CreateBusinessDto newBusiness
    ) {
        UserDto user = userService.getUserByUserId(newBusiness.getUserId());

        if(user.getVerified()) {
            return buildResponse.createResponse("business", businessService.addBusiness(newBusiness), "Business added", HttpStatus.CREATED);
        } else {
            return buildResponse.createResponse("business", "User is not yet verified", "Failed to add business", HttpStatus.FORBIDDEN);
        }


    }
    @PutMapping("business")
    @Override
    public ResponseEntity<Response> updateBusiness(
            @RequestBody UpdateBusinessDto updateBusinessDto
    ) {
        return buildResponse.createResponse("business", businessService.updateBusiness(updateBusinessDto), "Business updated", HttpStatus.OK);
    }
    @GetMapping("business/{businessId}")
    @Override
    public ResponseEntity<Response> getBusinessById(@PathVariable("businessId") Integer id) {
        return buildResponse.createResponse("business", businessService.getBusinessById(id), "Business fetched", HttpStatus.OK);
    }
    @GetMapping("business/user/{userId}")
    @Override
    public ResponseEntity<Response> getUserBusinesses(@PathVariable("userId") Integer userId) {
        return buildResponse.createResponse("business", businessService.getUserBusinesses(userId), "Businesses fetched", HttpStatus.OK);
    }
}
