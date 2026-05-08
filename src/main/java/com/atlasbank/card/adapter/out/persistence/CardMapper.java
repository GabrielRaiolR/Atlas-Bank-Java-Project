package com.atlasbank.card.adapter.out.persistence;

import com.atlasbank.card.domain.model.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public Card toDomain(CardJpaEntity e) {
        if (e == null) return null;
        return Card.restore(e.getId(), e.getAccountId(), e.getCreditLimit(), e.getAvailableLimit(),
                e.getStatus(), e.getPanLastFour(), e.getVersion(),
                e.getCreatedAt(), e.getUpdatedAt());
    }

    public CardJpaEntity toEntity(Card c) {
        if (c == null) return null;
        return CardJpaEntity.builder()
                .id(c.getId())
                .accountId(c.getAccountId())
                .creditLimit(c.getCreditLimit())
                .availableLimit(c.getAvailableLimit())
                .status(c.getStatus())
                .panLastFour(c.getPanLastFour())
                .version(c.getVersion())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}
