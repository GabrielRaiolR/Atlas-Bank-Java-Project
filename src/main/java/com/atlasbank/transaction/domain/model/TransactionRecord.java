package com.atlasbank.transaction.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionRecord(
        UUID id,
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        String idempotencyKey,
        String description,
        OffsetDateTime createdAt
) {
    public static TransactionRecord completed(UUID from, UUID to, BigDecimal amount, TransactionType type, String idempotencyKey, String description) {
        return new TransactionRecord(
                UUID.randomUUID(), from, to, amount,type,
                TransactionStatus.COMPLETED, idempotencyKey, description, OffsetDateTime.now()
        );
    }
}
