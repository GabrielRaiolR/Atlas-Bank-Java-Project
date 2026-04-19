package com.atlasbank.account.application;

import com.atlasbank.account.domain.model.Account;
import com.atlasbank.account.domain.port.in.OpenAccountUseCase;
import com.atlasbank.account.domain.port.out.SaveAccountPort;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class OpenAccountService implements OpenAccountUseCase {

    private final SaveAccountPort saveAccountPort;

    @Override
    public Account execute(OpenAccountCommand command) {
        String number = generateNumber();
        Account account = Account.open(command.customerId(), number);
        return saveAccountPort.save(account);
    }

    private String generateNumber() {
        return String.format("%010d", ThreadLocalRandom.current().nextInt(1_000_000_000));
    }
}
