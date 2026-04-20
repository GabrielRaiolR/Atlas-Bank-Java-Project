package com.atlasbank.transaction.domain.port.out;

import com.atlasbank.transaction.domain.model.TransactionRecord;

import java.util.Optional;

public interface LoadTransactionByIdempotencyPort {
    Optional<TransactionRecord> findByKey(String key);
}
