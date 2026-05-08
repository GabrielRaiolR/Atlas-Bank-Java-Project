package com.atlasbank.card.adapter.out.messaging;

import com.atlasbank.card.domain.port.out.PublishPurchasePort;
import com.atlasbank.common.event.CardPurchaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCardPurchasePublisher implements PublishPurchasePort {

    static final String TOPIC = "atlas.card.purchase";

    private final KafkaTemplate<String, Object>  kafkaTemplate;

    @Override
    public void publish(CardPurchaseEvent event) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    send(event);
                }
            });
        } else {
            send(event);
        }
    }

    private void send(CardPurchaseEvent event) {
        kafkaTemplate.send(TOPIC, event.cardId().toString(), event);
        log.info("published card purchase event card-id={} account-id={}",
                event.cardId(), event.accountId());
    }
}
