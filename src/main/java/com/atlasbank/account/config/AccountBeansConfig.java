package com.atlasbank.account.config;

import com.atlasbank.account.application.GetAccountService;
import com.atlasbank.account.application.OpenAccountService;
import com.atlasbank.account.domain.port.in.GetAccountUseCase;
import com.atlasbank.account.domain.port.in.OpenAccountUseCase;
import com.atlasbank.account.domain.port.out.LoadAccountPort;
import com.atlasbank.account.domain.port.out.SaveAccountPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountBeansConfig {

    @Bean
    public OpenAccountUseCase openAccountUseCase(SaveAccountPort saveAccountPort) {
        return new OpenAccountService(saveAccountPort);
    }

    @Bean
    public GetAccountUseCase getAccountUseCase(LoadAccountPort loadAccountPort) {
        return new GetAccountService(loadAccountPort);
    }
}
