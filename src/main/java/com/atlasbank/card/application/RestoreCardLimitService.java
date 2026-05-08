package com.atlasbank.card.application;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.in.RestoreCardLimitUseCase;
import com.atlasbank.card.domain.port.out.LoadCardPort;
import com.atlasbank.card.domain.port.out.SaveCardPort;
import com.atlasbank.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class RestoreCardLimitService implements RestoreCardLimitUseCase {

    private final LoadCardPort loadCardPort;
    private final SaveCardPort saveCardPort;

    @Override
    @Transactional
    public void execute(UUID cardId, BigDecimal paidAmount) {
        Card card = loadCardPort.loadById(cardId).orElseThrow(() -> new NotFoundException("Card not found"));
        card.restoreLimit(paidAmount);
        saveCardPort.save(card);
    }
}
