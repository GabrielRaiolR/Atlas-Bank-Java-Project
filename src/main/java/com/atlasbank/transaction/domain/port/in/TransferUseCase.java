package com.atlasbank.transaction.domain.port.in;

import com.atlasbank.transaction.domain.model.TransactionRecord;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferUseCase {
    TransactionRecord execute(TransferCommand command);

    record TransferCommand(
            UUID fromAccountId,
            UUID toAccountId,
            BigDecimal amount,
            String description,
            String idempotencyKey
    ) {}
}
