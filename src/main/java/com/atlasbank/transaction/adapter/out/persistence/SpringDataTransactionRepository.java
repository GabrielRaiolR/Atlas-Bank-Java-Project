package com.atlasbank.transaction.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionJpaEntity, UUID> {
    Optional<TransactionJpaEntity> findByIdempotencyKey(String key);
}
