package com.atlasbank.card.application;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.in.GetCardUseCase;
import com.atlasbank.card.domain.port.out.LoadCardPort;
import com.atlasbank.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetCardService implements GetCardUseCase {

    private final LoadCardPort loadCardPort;

    @Override
    public Card byId(UUID id) {
        return loadCardPort.loadById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
    }
}
