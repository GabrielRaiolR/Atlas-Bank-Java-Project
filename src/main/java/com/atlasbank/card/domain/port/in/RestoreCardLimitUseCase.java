package com.atlasbank.card.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface RestoreCardLimitUseCase {
    void execute(UUID cardId, BigDecimal paidAmount);
}
