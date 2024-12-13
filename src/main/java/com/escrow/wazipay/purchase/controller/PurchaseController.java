package com.escrow.wazipay.purchase.controller;

import com.escrow.wazipay.purchase.dto.*;
import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface PurchaseController {
    ResponseEntity<Response> createEscrowTransaction(CreateEscrowTransactionDto createEscrowTransactionDto, User user);
    ResponseEntity<Response> updateEscrowTransaction(UpdateEscrowTransactionDto updateEscrowTransactionDto, User user);
    ResponseEntity<Response> getEscrowTransaction(Integer transactionId);
    ResponseEntity<Response> depositToEscrow(Integer transactionId, User user);
    ResponseEntity<Response> assignToDeliveryMan(CreateDeliveryAssignmentDto createDeliveryAssignmentDto, User user);
    ResponseEntity<Response> authorizePurchase(Integer transactionId, User user);

}
