package com.atlasbank.transaction.domain.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface PublishTransferEventPort {
    void publish(TransferCompletedEvent event);

    record TransferCompletedEvent(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {}
}
