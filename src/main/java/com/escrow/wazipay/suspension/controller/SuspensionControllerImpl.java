package com.escrow.wazipay.suspension.controller;

import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspensionReasonUpdateDto;
import com.escrow.wazipay.suspension.service.SuspensionService;
import com.escrow.wazipay.user.dto.UserDto;
import com.escrow.wazipay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class SuspensionControllerImpl implements SuspensionController{
    private final BuildResponse buildResponse = new BuildResponse();
    private final SuspensionService suspensionService;
    private final UserService userService;
    @Autowired
    public SuspensionControllerImpl(
            SuspensionService suspensionService,
            UserService userService
    ) {
        this.suspensionService = suspensionService;
        this.userService = userService;
    }
    @PostMapping("suspension")
    @Override
    public ResponseEntity<Response> suspendUser(
            @RequestBody SuspendUserDto suspendUserDto,
            @AuthenticationPrincipal User user
    ) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        UserDto userToSuspend = userService.getUserByUserId(suspendUserDto.getUserId());

        boolean isSuspended = userToSuspend.getSuspended();

        if(isSuspended) {
            if(isAdmin) {
                return buildResponse.createResponse("suspension", suspensionService.suspendUser(suspendUserDto), "User suspended", HttpStatus.CREATED);
            } else {
                return buildResponse.createResponse("suspension", "You are forbidden to do this action", "FAIL", HttpStatus.FORBIDDEN);
            }
        } else {
            return buildResponse.createResponse("suspension", "User is already suspended", "FAIL", HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("suspension/reason")
    @Override
    public ResponseEntity<Response> updateSuspensionReason(
            @RequestBody SuspensionReasonUpdateDto suspensionDto,
            @AuthenticationPrincipal User user
    ) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if(isAdmin) {
            return buildResponse.createResponse("suspension", suspensionService.updateSuspensionReason(suspensionDto), "Suspension reason updated", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("suspension", "You are forbidden to do this action", "FAIL", HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("suspension/lift/reason")
    @Override
    public ResponseEntity<Response> updateSuspensionLiftReason(
            @RequestBody LiftSuspensionDto liftSuspensionDto,
            @AuthenticationPrincipal User user
    ) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if(isAdmin) {
            return buildResponse.createResponse("suspension", suspensionService.updateSuspensionLiftReason(liftSuspensionDto), "Suspension lift reason updated", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("suspension", "You are forbidden to do this action", "FAIL", HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("suspension")
    @Override
    public ResponseEntity<Response> liftSuspension(
            @RequestBody LiftSuspensionDto liftSuspensionDto,
            @AuthenticationPrincipal User user
    ) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if(isAdmin) {
            return buildResponse.createResponse("suspension", suspensionService.liftSuspension(liftSuspensionDto), "Suspension lifted", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("suspension", "You are forbidden to do this action", "FAIL", HttpStatus.FORBIDDEN);
        }
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
    @GetMapping("suspension/all")
    @Override
    public ResponseEntity<Response> getAllSuspensions() {
        return buildResponse.createResponse("suspension", suspensionService.getAllSuspensions(), "Suspensions fetched", HttpStatus.OK);
    }
}
