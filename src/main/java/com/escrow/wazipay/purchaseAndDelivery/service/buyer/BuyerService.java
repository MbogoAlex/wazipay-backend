package com.escrow.wazipay.purchaseAndDelivery.service.buyer;

import com.escrow.wazipay.purchaseAndDelivery.dto.EscrowTransactionDto;

public interface BuyerService {
    EscrowTransactionDto directDepositToEscrow(Integer transactionId, Integer buyerId);
    EscrowTransactionDto getEscrowTransaction(Integer transactionId);

}
