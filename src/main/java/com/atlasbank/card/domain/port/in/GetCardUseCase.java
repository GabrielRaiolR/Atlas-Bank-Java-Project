package com.atlasbank.card.domain.port.in;

import com.atlasbank.card.domain.model.Card;

import java.util.UUID;

public interface GetCardUseCase {
    Card byId(UUID id);
}
