package com.escrow.wazipay.purchaseAndDelivery.service.seller;

import com.escrow.wazipay.purchaseAndDelivery.dto.CreateEscrowTransactionDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.EscrowTransactionDto;
import com.escrow.wazipay.purchaseAndDelivery.dto.UpdateEscrowTransactionDto;

public interface SellerService {
    EscrowTransactionDto createEscrowTransaction(CreateEscrowTransactionDto createEscrowTransactionDto, Integer sellerId);
    EscrowTransactionDto sellerUpdateEscrowTransaction(UpdateEscrowTransactionDto updateEscrowTransactionDto);
}
