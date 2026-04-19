package com.atlasbank.account.adapter.out.persistence;

import com.atlasbank.account.domain.model.Account;
import com.atlasbank.account.domain.port.out.LoadAccountPort;
import com.atlasbank.account.domain.port.out.SaveAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {

    private final SpringDataAccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Optional<Account> loadById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountJpaEntity saved = repository.save(mapper.toEntity(account));
        return mapper.toDomain(saved);
    }

}
