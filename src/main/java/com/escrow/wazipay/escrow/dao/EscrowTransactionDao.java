package com.escrow.wazipay.escrow.dao;

import com.escrow.wazipay.escrow.entity.EscrowTransaction;

public interface EscrowTransactionDao {
    EscrowTransaction createEscrowTransaction(EscrowTransaction escrowTransaction);
    EscrowTransaction updateEscrowTransaction(EscrowTransaction escrowTransaction);

    EscrowTransaction getEscrowTransaction(Integer id);
}
