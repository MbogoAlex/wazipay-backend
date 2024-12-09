package com.escrow.wazipay.suspension.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.service.SuspensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SuspensionControllerImpl implements SuspensionController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final SuspensionService suspensionService;
    @Autowired
    public SuspensionControllerImpl(SuspensionService suspensionService) {
        this.suspensionService = suspensionService;
    }
    @PostMapping("suspension")
    @Override
    public ResponseEntity<Response> suspendUser(@RequestBody SuspendUserDto suspendUserDto) {
        return buildResponse.createResponse("suspension", suspensionService.suspendUser(suspendUserDto), "User suspended", HttpStatus.CREATED);
    }
    @PutMapping("suspension/reason")
    @Override
    public ResponseEntity<Response> updateSuspensionReason(@RequestBody SuspensionDto suspensionDto) {
        return buildResponse.createResponse("suspension", suspensionService.updateSuspensionReason(suspensionDto), "Suspension reason updated", HttpStatus.OK);
    }
    @PutMapping("suspension/lift/reason")
    @Override
    public ResponseEntity<Response> updateSuspensionLiftReason(@RequestBody LiftSuspensionDto liftSuspensionDto) {
        return buildResponse.createResponse("suspension", suspensionService.updateSuspensionLiftReason(liftSuspensionDto), "Suspension lift reason updated", HttpStatus.OK);
    }
    @PutMapping("suspension")
    @Override
    public ResponseEntity<Response> liftSuspension(@RequestBody LiftSuspensionDto liftSuspensionDto) {
        return buildResponse.createResponse("suspension", suspensionService.liftSuspension(liftSuspensionDto), "Suspension lifted", HttpStatus.OK);
    }
    @GetMapping("suspension/{id}")
    @Override
    public ResponseEntity<Response> getSuspension(@PathVariable("id") Integer id) {
        return buildResponse.createResponse("suspension", suspensionService.getSuspension(id), "Suspension fetched", HttpStatus.OK);
    }
    @GetMapping("suspension/user/{id}")
    @Override
    public ResponseEntity<Response> getUserSuspensions(@PathVariable("id") Integer userId) {
        return buildResponse.createResponse("suspension", suspensionService.getUserSuspensions(userId), "User suspensions fetched", HttpStatus.OK);
    }
}
