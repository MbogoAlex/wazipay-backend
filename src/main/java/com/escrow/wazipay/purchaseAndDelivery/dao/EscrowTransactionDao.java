package com.escrow.wazipay.purchaseAndDelivery.dao;

import com.escrow.wazipay.purchaseAndDelivery.entity.EscrowTransaction;

import java.time.LocalDateTime;
import java.util.List;

public interface EscrowTransactionDao {
    EscrowTransaction createEscrowTransaction(EscrowTransaction escrowTransaction);
    EscrowTransaction updateEscrowTransaction(EscrowTransaction escrowTransaction);

    EscrowTransaction getEscrowTransaction(Integer id);
    Boolean existsByPurchaseCode(String purchaseCode);

    List<EscrowTransaction> getSellerEscrowTransactions(Integer userId);
    List<EscrowTransaction> getBuyerEscrowTransactions(Integer userId, String status, String searchQuery, LocalDateTime startDate, LocalDateTime endDate);
    List<EscrowTransaction> getAllTransactions();

}
