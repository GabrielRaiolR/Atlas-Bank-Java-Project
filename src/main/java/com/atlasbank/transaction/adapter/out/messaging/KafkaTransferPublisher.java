package com.atlasbank.transaction.adapter.out.messaging;

import com.atlasbank.transaction.domain.port.out.PublishTransferEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransferPublisher implements PublishTransferEventPort {

    private static final String TOPIC = "atlas.transfer.completed";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(TransferCompletedEvent event) {
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

    private void send(TransferCompletedEvent event) {
        kafkaTemplate.send(TOPIC, event.fromAccountId().toString(), event);
        log.info("published transfer completed event id-from={} id-to={}",
                event.fromAccountId(), event.toAccountId());
    }
}