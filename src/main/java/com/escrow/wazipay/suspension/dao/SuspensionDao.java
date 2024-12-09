package com.escrow.wazipay.suspension.dao;

import com.escrow.wazipay.suspension.dto.LiftSuspensionDto;
import com.escrow.wazipay.suspension.dto.SuspendUserDto;
import com.escrow.wazipay.suspension.dto.SuspensionDto;
import com.escrow.wazipay.suspension.entity.Suspension;

import java.util.List;

public interface SuspensionDao {
    Suspension suspendUser(Suspension suspension);
    Suspension updateSuspension(Suspension suspension);


    Suspension getSuspension(Integer id);

    List<Suspension> getUserSuspensions(Integer userId);

    List<Suspension> getAllSuspensions();
}
