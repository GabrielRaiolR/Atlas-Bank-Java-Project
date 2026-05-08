package com.atlasbank.card.adapter.out.persistence;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.out.LoadCardPort;
import com.atlasbank.card.domain.port.out.SaveCardPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardPersistenceAdapter implements LoadCardPort, SaveCardPort {

    private final SpringDataCardRepository repository;

    private final CardMapper mapper;

    @Override
    public Optional<Card> loadById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Card save(Card card) {
        CardJpaEntity saved = repository.save(mapper.toEntity(card));
        return mapper.toDomain(saved);
    }
}
