package com.atlasbank.account.application;

import com.atlasbank.account.domain.model.Account;
import com.atlasbank.account.domain.port.in.GetAccountUseCase;
import com.atlasbank.account.domain.port.out.LoadAccountPort;
import com.atlasbank.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetAccountService implements GetAccountUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Account byId(UUID id) {
        return loadAccountPort.loadById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }
}
