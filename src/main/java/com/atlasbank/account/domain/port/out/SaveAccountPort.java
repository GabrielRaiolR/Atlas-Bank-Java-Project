package com.atlasbank.account.domain.port.out;

import com.atlasbank.account.domain.model.Account;

public interface SaveAccountPort {
    Account save(Account account);
}
