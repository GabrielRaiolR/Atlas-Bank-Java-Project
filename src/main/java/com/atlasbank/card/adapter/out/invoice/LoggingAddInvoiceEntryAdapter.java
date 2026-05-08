package com.atlasbank.card.adapter.out.invoice;

import com.atlasbank.card.domain.port.out.AddInvoiceEntryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingAddInvoiceEntryAdapter implements AddInvoiceEntryPort {

    @Override
    public void execute(AddInvoiceEntryCommand command) {
        log.warn("[TEMP] entrada de fatura registrada apenas em log " + "(invoice ainda não implementado): card-id={} amount={} merchant={}", command.cardId(), command.amount(), command.merchant());
    }
}
