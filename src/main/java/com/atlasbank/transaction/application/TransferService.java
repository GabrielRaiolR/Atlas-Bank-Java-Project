package com.atlasbank.transaction.application;

import com.atlasbank.account.domain.model.Account;
import com.atlasbank.account.domain.port.out.LoadAccountPort;
import com.atlasbank.account.domain.port.out.SaveAccountPort;
import com.atlasbank.common.exception.BusinessException;
import com.atlasbank.common.exception.NotFoundException;
import com.atlasbank.transaction.domain.model.TransactionRecord;
import com.atlasbank.transaction.domain.model.TransactionType;
import com.atlasbank.transaction.domain.port.in.TransferUseCase;
import com.atlasbank.transaction.domain.port.out.LoadTransactionByIdempotencyPort;
import com.atlasbank.transaction.domain.port.out.PublishTransferEventPort;
import com.atlasbank.transaction.domain.port.out.PublishTransferEventPort.TransferCompletedEvent;
import com.atlasbank.transaction.domain.port.out.SaveTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TransferService implements TransferUseCase {

    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;
    private final SaveTransactionPort saveTransactionPort;
    private final LoadTransactionByIdempotencyPort loadByIdempotency;
    private final PublishTransferEventPort publishPort;

    @Override
    @Transactional
    public TransactionRecord execute(TransferCommand command) {
        if (command.fromAccountId().equals(command.toAccountId())) {
            throw new BusinessException("SAME_ACCOUNT", "conta origem e destino não podem ser iguais");
        }

        if (command.idempotencyKey() != null) {
            var existing = loadByIdempotency.findByKey(command.idempotencyKey());
            if (existing.isPresent()) return existing.get();
        }

        Account from = loadAccountPort.loadById(command.fromAccountId())
                .orElseThrow(() -> new NotFoundException("conta origem não encontrada"));
        Account to = loadAccountPort.loadById(command.toAccountId())
                .orElseThrow(() -> new NotFoundException("conta destino não encontrada"));

        from.withdraw(command.amount());
        to.deposit(command.amount());

        saveAccountPort.save(from);
        saveAccountPort.save(to);

        TransactionRecord record = TransactionRecord.completed(
                from.getId(), to.getId(), command.amount(),
                TransactionType.TRANSFER, command.idempotencyKey(), command.description()
        );
        TransactionRecord saved = saveTransactionPort.save(record);

        publishPort.publish(new TransferCompletedEvent(
                from.getId(), to.getId(), command.amount()
        ));

        return saved;
    }
}