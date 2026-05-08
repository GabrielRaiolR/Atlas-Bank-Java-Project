package com.atlasbank.card.domain.port.in;

import com.atlasbank.card.domain.model.Card;

import java.math.BigDecimal;
import java.util.UUID;

public interface IssueCardUseCase {

    Card execute(IssueCardCommand command);

    record IssueCardCommand(UUID accountId, BigDecimal creditLimit, String panLastFour) {}
}
