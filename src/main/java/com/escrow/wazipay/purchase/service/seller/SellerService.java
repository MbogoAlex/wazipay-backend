package com.escrow.wazipay.purchase.service.seller;

import com.escrow.wazipay.purchase.dto.CreateEscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.EscrowTransactionDto;
import com.escrow.wazipay.purchase.dto.UpdateEscrowTransactionDto;

public interface SellerService {
    EscrowTransactionDto createEscrowTransaction(CreateEscrowTransactionDto createEscrowTransactionDto, Integer sellerId);
    EscrowTransactionDto sellerUpdateEscrowTransaction(UpdateEscrowTransactionDto updateEscrowTransactionDto);
}
