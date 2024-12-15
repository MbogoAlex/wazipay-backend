package com.escrow.wazipay.admin.controller;

import com.escrow.wazipay.admin.service.AdminService;
import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AdminControllerImpl implements AdminController{

    private final BuildResponse buildResponse = new BuildResponse();
    private final AdminService adminService;
    @Autowired
    public AdminControllerImpl(
            AdminService adminService
    ) {
        this.adminService = adminService;
    }
    @PutMapping("admin/create/{userId}")
    @Override
    public ResponseEntity<Response> setAdmin(@PathVariable("userId") Integer userId) {
        return buildResponse.createResponse("admin", adminService.setAdmin(userId), "Admin created", HttpStatus.OK);
    }
}
