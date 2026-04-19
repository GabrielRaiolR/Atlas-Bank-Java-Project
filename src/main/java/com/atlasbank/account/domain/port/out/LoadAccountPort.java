package com.atlasbank.account.domain.port.out;

import com.atlasbank.account.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {
    Optional<Account> loadById(UUID id);
}
