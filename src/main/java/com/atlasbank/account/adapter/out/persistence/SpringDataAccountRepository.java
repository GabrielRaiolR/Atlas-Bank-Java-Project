package com.atlasbank.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, UUID> {
}
