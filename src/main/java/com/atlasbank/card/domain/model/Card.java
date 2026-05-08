package com.atlasbank.card.domain.model;

import com.atlasbank.card.domain.CardStatus;
import com.atlasbank.common.exception.BusinessException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Card {

    private final UUID id;
    private final UUID accountId;
    private final BigDecimal creditLimit;
    private BigDecimal availableLimit;
    private CardStatus status;
    private final String panLastFour;
    private long version;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Card(UUID id, UUID accountId, BigDecimal creditLimit, BigDecimal availableLimit,
                 CardStatus status, String panLastFour, long version,
                 OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.creditLimit = creditLimit;
        this.availableLimit = availableLimit;
        this.status = status;
        this.panLastFour = panLastFour;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Card issue(UUID accountId, BigDecimal creditLimit, String panLastFour) {
        if (creditLimit == null || creditLimit.signum() <= 0) {
            throw new BusinessException("INVALID_LIMIT", "limite de crédito deve ser positivo");
        }
        if (panLastFour == null || !panLastFour.matches("\\d{4}")) {
            throw new BusinessException("INVALID_PAN", "panLastFour deve ter 4 dígitos");
        }
        OffsetDateTime now = OffsetDateTime.now();
        return new Card(UUID.randomUUID(), accountId, creditLimit, creditLimit,
                CardStatus.ACTIVE, panLastFour, 0L, now, now);
    }

    public static Card restore(UUID id, UUID accountId, BigDecimal creditLimit,
                               BigDecimal availableLimit, CardStatus status, String panLastFour,
                               long version, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Card(id, accountId, creditLimit, availableLimit, status, panLastFour,
                version, createdAt, updatedAt);
    }

    public void purchase(BigDecimal amount) {
        ensureActive();
        requirePositive(amount);
        if (this.availableLimit.compareTo(amount) < 0) {
            throw new BusinessException("INSUFFICIENT_LIMIT", "limite insuficiente");
        }
        this.availableLimit = this.availableLimit.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void restoreLimit(BigDecimal amount) {
        requirePositive(amount);
        BigDecimal candidate = this.availableLimit.add(amount);
        this.availableLimit = candidate.compareTo(this.creditLimit) > 0
                ? this.creditLimit
                : candidate;
        this.updatedAt = OffsetDateTime.now();
    }

    public void block() {
        this.status = CardStatus.BLOCKED;
        this.updatedAt = OffsetDateTime.now();
    }

    private void ensureActive() {
        if (this.status != CardStatus.ACTIVE) {
            throw new BusinessException("CARD_NOT_ACTIVE", "cartão não está ativo");
        }
    }

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "valor deve ser positivo");
        }
    }

    public UUID getId() { return id; }
    public UUID getAccountId() { return accountId; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public BigDecimal getAvailableLimit() { return availableLimit; }
    public CardStatus getStatus() { return status; }
    public String getPanLastFour() { return panLastFour; }
    public long getVersion() { return version; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
