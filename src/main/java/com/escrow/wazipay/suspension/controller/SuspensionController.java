package com.escrow.wazipay.suspension.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspensionReasonUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface SuspensionController {
    ResponseEntity<Response> suspendUser(SuspendUserDto suspendUserDto, User user);
    ResponseEntity<Response>  updateSuspensionReason(SuspensionReasonUpdateDto suspensionDto, User user);
    ResponseEntity<Response>  updateSuspensionLiftReason(LiftSuspensionDto liftSuspensionDto, User user);

    ResponseEntity<Response>  liftSuspension(LiftSuspensionDto liftSuspensionDto, User user);

    ResponseEntity<Response>  getSuspension(Integer id);

    ResponseEntity<Response>  getUserSuspensions(Integer userId);
    ResponseEntity<Response> getAllSuspensions();
}
