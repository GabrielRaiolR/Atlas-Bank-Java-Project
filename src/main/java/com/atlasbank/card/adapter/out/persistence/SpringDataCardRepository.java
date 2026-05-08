package com.atlasbank.card.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCardRepository extends JpaRepository<CardJpaEntity, UUID> {

}
