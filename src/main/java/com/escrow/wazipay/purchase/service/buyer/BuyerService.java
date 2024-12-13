package com.escrow.wazipay.purchase.service.buyer;

import com.escrow.wazipay.purchase.dto.EscrowTransactionDto;

public interface BuyerService {
    EscrowTransactionDto directDepositToEscrow(Integer transactionId, Integer buyerId);
    EscrowTransactionDto getEscrowTransaction(Integer transactionId);

}
