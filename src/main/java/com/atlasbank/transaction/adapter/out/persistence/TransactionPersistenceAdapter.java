package com.atlasbank.transaction.adapter.out.persistence;

import com.atlasbank.transaction.domain.model.TransactionRecord;
import com.atlasbank.transaction.domain.port.out.LoadTransactionByIdempotencyPort;
import com.atlasbank.transaction.domain.port.out.SaveTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements SaveTransactionPort, LoadTransactionByIdempotencyPort {

    private final SpringDataTransactionRepository repository;

    @Override
    public TransactionRecord save(TransactionRecord r) {
        TransactionJpaEntity e = TransactionJpaEntity.builder()
                .id(r.id())
                .fromAccountId(r.fromAccountId())
                .toAccountId(r.toAccountId())
                .amount(r.amount())
                .type(r.type())
                .status(r.status())
                .idempotencyKey(r.idempotencyKey())
                .description(r.description())
                .createdAt(r.createdAt())
                .build();
        TransactionJpaEntity saved = repository.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<TransactionRecord> findByKey(String key) {
        return repository.findByIdempotencyKey(key).map(this::toDomain);
    }

    private TransactionRecord toDomain(TransactionJpaEntity e) {
        return new TransactionRecord(e.getId(), e.getFromAccountId(), e.getToAccountId(),
                e.getAmount(), e.getType(), e.getStatus(), e.getIdempotencyKey(),
                e.getDescription(), e.getCreatedAt());
    }

}
