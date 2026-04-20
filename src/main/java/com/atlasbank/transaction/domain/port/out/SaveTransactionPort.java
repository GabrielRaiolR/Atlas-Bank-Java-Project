package com.atlasbank.transaction.domain.port.out;

import com.atlasbank.transaction.domain.model.TransactionRecord;

public interface SaveTransactionPort {
    TransactionRecord save(TransactionRecord record);
}
