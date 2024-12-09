package com.escrow.wazipay.suspension.controller;

import com.escrow.wazipay.response.Response;
import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import org.springframework.http.ResponseEntity;

public interface SuspensionController {
    ResponseEntity<Response> suspendUser(SuspendUserDto suspendUserDto);
    ResponseEntity<Response>  updateSuspensionReason(SuspensionDto suspensionDto);
    ResponseEntity<Response>  updateSuspensionLiftReason(LiftSuspensionDto liftSuspensionDto);

    ResponseEntity<Response>  liftSuspension(LiftSuspensionDto liftSuspensionDto);

    ResponseEntity<Response>  getSuspension(Integer id);

    ResponseEntity<Response>  getUserSuspensions(Integer userId);
}
