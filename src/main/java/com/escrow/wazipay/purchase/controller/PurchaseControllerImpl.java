package com.escrow.wazipay.purchase.controller;

import com.escrow.wazipay.purchase.dto.*;
import com.escrow.wazipay.purchase.service.buyer.BuyerService;
import com.escrow.wazipay.purchase.service.delivery.DeliveryService;
import com.escrow.wazipay.purchase.service.seller.SellerService;
import com.escrow.wazipay.response.BuildResponse;
import com.escrow.wazipay.response.Response;
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
public class PurchaseControllerImpl implements PurchaseController {
    private final BuildResponse buildResponse = new BuildResponse();
    private final BuyerService buyerService;
    private final DeliveryService deliveryService;
    private final SellerService sellerService;
    private final UserService userService;
    @Autowired
    public PurchaseControllerImpl(
            BuyerService buyerService,
            DeliveryService deliveryService,
            SellerService sellerService,
            UserService userService
    ) {
        this.buyerService = buyerService;
        this.deliveryService = deliveryService;
        this.sellerService = sellerService;
        this.userService = userService;
    }
    @PostMapping("payment")
    @Override
    public ResponseEntity<Response> createEscrowTransaction(
            @RequestBody CreateEscrowTransactionDto createEscrowTransactionDto,
            @AuthenticationPrincipal User user
            ) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        boolean isSeller = user.getAuthorities()
                .stream().anyMatch(role -> role.getAuthority().equals("SELLER"));

        UserDto userDto = userService.getUserByPhoneNumber(user.getUsername());

        if(isSeller) {
            return buildResponse.createResponse("transaction", sellerService.createEscrowTransaction(createEscrowTransactionDto, userDto.getUserId()), "Transaction created", HttpStatus.CREATED);
        } else {
            return buildResponse.createResponse("transaction", "You are not authorized to do this action", "FAILED", HttpStatus.FORBIDDEN);
        }

    }
    @PutMapping("payment")
    @Override
    public ResponseEntity<Response> updateEscrowTransaction(
            @RequestBody UpdateEscrowTransactionDto updateEscrowTransactionDto,
            @AuthenticationPrincipal User user
    ) {
        boolean isSeller = user.getAuthorities()
                .stream().anyMatch(role -> role.getAuthority().equals("SELLER"));

        UserDto userDto = userService.getUserByPhoneNumber(user.getUsername());

        if(isSeller) {
            return buildResponse.createResponse("transaction", sellerService.sellerUpdateEscrowTransaction(updateEscrowTransactionDto), "Transaction updated", HttpStatus.OK);
        } else {
            return buildResponse.createResponse("transaction", "You are not authorized to do this action", "FAILED", HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("payment/details/{transactionId}")
    @Override
    public ResponseEntity<Response> getEscrowTransaction(
            @PathVariable("transactionId") Integer transactionId
    ) {
        return buildResponse.createResponse("transaction", buyerService.getEscrowTransaction(transactionId), "payment details fetched", HttpStatus.OK);
    }

    @PutMapping("payment/pay/{transactionId}")
    @Override
    public ResponseEntity<Response> depositToEscrow(
            @PathVariable("transactionId") Integer transactionId,
            @AuthenticationPrincipal User user
    ) {
        UserDto userDto = userService.getUserByPhoneNumber(user.getUsername());

        return buildResponse.createResponse("transaction", buyerService.directDepositToEscrow(transactionId, userDto.getUserId()), "Payment successful", HttpStatus.OK);
    }
    @PostMapping("delivery/assignment")
    @Override
    public ResponseEntity<Response> assignToDeliveryMan(
            @RequestBody CreateDeliveryAssignmentDto createDeliveryAssignmentDto,
            @AuthenticationPrincipal User user
    ) {
        UserDto userDto = userService.getUserByPhoneNumber(user.getUsername());

        return buildResponse.createResponse("delivery", deliveryService.createDeliveryAssignment(createDeliveryAssignmentDto), "Assignment done", HttpStatus.CREATED);
    }
    @PostMapping("payment/authorize/{transactionId}")
    @Override
    public ResponseEntity<Response> authorizePurchase(
            @PathVariable("transactionId") Integer transactionId,
            @AuthenticationPrincipal User user
    ) {
        return buildResponse.createResponse("purchase", deliveryService.authorizePayment(transactionId), "Payment authorized", HttpStatus.OK);
    }
}
