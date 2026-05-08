package com.atlasbank.card.domain.port.out;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface AddInvoiceEntryPort {
    void execute(AddInvoiceEntryCommand command);

    record AddInvoiceEntryCommand(UUID cardId, BigDecimal amount, String merchant, OffsetDateTime occurredAt) {
    }
}
