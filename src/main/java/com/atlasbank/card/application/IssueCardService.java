package com.atlasbank.card.application;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.in.IssueCardUseCase;
import com.atlasbank.card.domain.port.out.SaveCardPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueCardService implements IssueCardUseCase {

    private final SaveCardPort saveCardPort;

    @Override
    public Card execute(IssueCardCommand command) {
        Card card = Card.issue(command.accountId(), command.creditLimit(), command.panLastFour());
        return saveCardPort.save(card);
    }
}
