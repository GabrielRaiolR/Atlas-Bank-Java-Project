package com.atlasbank.card.domain.port.out;

import com.atlasbank.card.domain.model.Card;

import java.util.Optional;
import java.util.UUID;

public interface LoadCardPort {
    Optional<Card> loadById(UUID id);
}
