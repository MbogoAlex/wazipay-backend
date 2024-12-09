package com.escrow.wazipay.suspension.service;

import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspensionReasonUpdateDto;

import java.util.List;

public interface SuspensionService {
    SuspensionDto suspendUser(SuspendUserDto suspendUserDto);
    SuspensionDto updateSuspensionReason(SuspensionReasonUpdateDto suspensionDto);
    SuspensionDto updateSuspensionLiftReason(LiftSuspensionDto liftSuspensionDto);

    SuspensionDto liftSuspension(LiftSuspensionDto liftSuspensionDto);

    SuspensionDto getSuspension(Integer id);

    List<SuspensionDto> getUserSuspensions(Integer userId);

    List<SuspensionDto> getAllSuspensions();
}
