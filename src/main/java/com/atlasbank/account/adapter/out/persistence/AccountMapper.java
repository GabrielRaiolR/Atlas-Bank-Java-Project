package com.atlasbank.account.adapter.out.persistence;

import com.atlasbank.account.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toDomain(AccountJpaEntity e) {
        if (e == null) return null;
        return Account.restore(
                e.getId(), e.getCustomerId(), e.getNumber(),
                e.getBalance(), e.getStatus(), e.getVersion(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }

    public AccountJpaEntity toEntity(Account a) {
        if (a == null) return null;
        return AccountJpaEntity.builder()
                .id(a.getId())
                .customerId(a.getCustomerId())
                .number(a.getNumber())
                .balance(a.getBalance())
                .status(a.getStatus())
                .version(a.getVersion())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }
}
