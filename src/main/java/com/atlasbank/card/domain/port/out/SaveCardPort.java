package com.atlasbank.card.domain.port.out;

import com.atlasbank.card.domain.model.Card;

public interface SaveCardPort {
    Card save(Card card);
}
