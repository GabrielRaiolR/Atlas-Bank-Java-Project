package com.atlasbank.card.application;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.in.PurchaseUseCase;
import com.atlasbank.card.domain.port.out.AddInvoiceEntryPort;
import com.atlasbank.card.domain.port.out.LoadCardPort;
import com.atlasbank.card.domain.port.out.PublishPurchasePort;
import com.atlasbank.card.domain.port.out.SaveCardPort;
import com.atlasbank.common.event.CardPurchaseEvent;
import com.atlasbank.common.exception.NotFoundException;
import com.atlasbank.card.domain.port.out.AddInvoiceEntryPort.AddInvoiceEntryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class PurchaseService implements PurchaseUseCase {

    private final LoadCardPort loadCardPort;
    private final SaveCardPort saveCardPort;
    private final AddInvoiceEntryPort addInvoiceEntryPort;
    private final PublishPurchasePort publishPurchasePort;

    @Override
    @Transactional
    public PurchaseResult execute(PurchaseCommand command) {
        Card card = loadCardPort.loadById(command.cardId()).orElseThrow(() -> new NotFoundException("Card not found"));
        card.purchase(command.amount());
        Card saved = saveCardPort.save(card);
        OffsetDateTime occurredAt = OffsetDateTime.now();

        addInvoiceEntryPort.execute(new AddInvoiceEntryCommand(
                saved.getId(), command.amount(), command.merchant(), occurredAt
        ));

        publishPurchasePort.publish(new CardPurchaseEvent(
                saved.getId(), saved.getAccountId(),
                command.amount(), command.merchant(), occurredAt
        ));

        return new PurchaseResult(saved.getId(),
                command.amount(),
                saved.getAvailableLimit(), occurredAt);
    }
}
