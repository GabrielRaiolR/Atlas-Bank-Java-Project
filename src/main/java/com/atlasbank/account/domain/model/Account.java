package com.atlasbank.account.domain.model;

import com.atlasbank.common.exception.BusinessException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Account {

    private final UUID id;
    private final UUID customerId;
    private final String number;
    private BigDecimal balance;
    private AccountStatus status;
    private long version;
    private final OffsetDateTime createdAt;
    private  OffsetDateTime updatedAt;

    private Account(UUID id, UUID customerId, String number, BigDecimal balance, AccountStatus status, long version, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.number = number;
        this.balance = balance;
        this.status = status;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account open(UUID customerId, String number) {
        OffsetDateTime now = OffsetDateTime.now();
        return new Account(UUID.randomUUID(), customerId, number,
                BigDecimal.ZERO, AccountStatus.ACTIVE, 0L, now, now);
    }

    public static Account restore(UUID id, UUID customerId, String number, BigDecimal balance,
                                  AccountStatus status, long version,
                                  OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Account(id, customerId, number, balance, status, version, createdAt, updatedAt);
    }


    public void deposit(BigDecimal amount) {
        ensureActive();
        requirePositive(amount);
        this.balance = this.balance.add(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void withdraw(BigDecimal amount) {
        ensureActive();
        requirePositive(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void block() {
        this.status = AccountStatus.BLOCKED;
        this.updatedAt = OffsetDateTime.now();
    }

    private void ensureActive() {
        if (this.status != AccountStatus.ACTIVE) {
            throw new BusinessException("ACCOUNT_NOT_ACTIVE", "Account is not active");
        }
    }

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "Your balance should be positive");
        }
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public String getNumber() { return number; }
    public BigDecimal getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }
    public long getVersion() { return version; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}



