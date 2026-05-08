package com.atlasbank.common.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CardPurchaseEvent(
        UUID cardId,
        UUID accountId,
        BigDecimal amount,
        String merchant,
        OffsetDateTime occurredAt
) {}
