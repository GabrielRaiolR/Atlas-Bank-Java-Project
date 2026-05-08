package com.atlasbank.card.domain.port.out;

import com.atlasbank.common.event.CardPurchaseEvent;

public interface PublishPurchasePort {
    void publish(CardPurchaseEvent event);
}