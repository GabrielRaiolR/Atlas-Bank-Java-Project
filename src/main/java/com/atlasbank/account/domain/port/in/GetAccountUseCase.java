package com.atlasbank.account.domain.port.in;

import com.atlasbank.account.domain.model.Account;

import java.util.UUID;

public interface GetAccountUseCase {
    Account byId(UUID id);
}
