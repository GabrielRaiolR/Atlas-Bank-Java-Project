package com.atlasbank.transaction.config;

import com.atlasbank.account.domain.port.out.LoadAccountPort;
import com.atlasbank.account.domain.port.out.SaveAccountPort;
import com.atlasbank.transaction.application.TransferService;
import com.atlasbank.transaction.domain.port.in.TransferUseCase;
import com.atlasbank.transaction.domain.port.out.LoadTransactionByIdempotencyPort;
import com.atlasbank.transaction.domain.port.out.PublishTransferEventPort;
import com.atlasbank.transaction.domain.port.out.SaveTransactionPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionBeansConfig {

    @Bean
    public TransferUseCase transferUseCase(
            LoadAccountPort loadAccountPort,
            SaveAccountPort saveAccountPort,
            SaveTransactionPort saveTransactionPort,
            LoadTransactionByIdempotencyPort loadByIdempotency,
            PublishTransferEventPort publishPort
    ) {
        return new TransferService(loadAccountPort, saveAccountPort, saveTransactionPort, loadByIdempotency, publishPort);
    }
}
