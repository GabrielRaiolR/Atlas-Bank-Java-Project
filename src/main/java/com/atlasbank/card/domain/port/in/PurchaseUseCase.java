package com.atlasbank.card.domain.port.in;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface PurchaseUseCase {

    PurchaseResult execute(PurchaseCommand command);

    record PurchaseCommand(UUID cardId, BigDecimal amount, String merchant) {}

    record PurchaseResult(UUID cardId, BigDecimal amount, BigDecimal availableLimit,
                          OffsetDateTime occurredAt) {}
}
