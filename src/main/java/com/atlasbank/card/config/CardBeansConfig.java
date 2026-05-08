package com.atlasbank.card.config;

import com.atlasbank.card.application.GetCardService;
import com.atlasbank.card.application.IssueCardService;
import com.atlasbank.card.application.PurchaseService;
import com.atlasbank.card.application.RestoreCardLimitService;
import com.atlasbank.card.domain.port.in.GetCardUseCase;
import com.atlasbank.card.domain.port.in.IssueCardUseCase;
import com.atlasbank.card.domain.port.in.PurchaseUseCase;
import com.atlasbank.card.domain.port.in.RestoreCardLimitUseCase;
import com.atlasbank.card.domain.port.out.AddInvoiceEntryPort;
import com.atlasbank.card.domain.port.out.LoadCardPort;
import com.atlasbank.card.domain.port.out.PublishPurchasePort;
import com.atlasbank.card.domain.port.out.SaveCardPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardBeansConfig {

    @Bean
    public IssueCardUseCase issueCardUseCase(SaveCardPort saveCardPort) {
        return new IssueCardService(saveCardPort);
    }

    @Bean
    public GetCardUseCase getCardUseCase(LoadCardPort loadCardPort) {
        return new GetCardService(loadCardPort);
    }

    @Bean
    public PurchaseUseCase purchaseUseCase(LoadCardPort loadCardPort,
    SaveCardPort saveCardPort,
    AddInvoiceEntryPort addInvoiceEntryPort,
    PublishPurchasePort publishPurchasePort) {
        return new PurchaseService(loadCardPort, saveCardPort,
                addInvoiceEntryPort, publishPurchasePort);
    }

    @Bean
    public RestoreCardLimitUseCase restoreCardLimitUseCase(LoadCardPort loadCardPort,
                                                           SaveCardPort saveCardPort) {
        return new RestoreCardLimitService(loadCardPort, saveCardPort);
    }
}
