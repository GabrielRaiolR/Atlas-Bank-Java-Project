package com.atlasbank.customer.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Customer(
        UUID id,
        String document,
        String fullName,
        String email,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static Customer create(String document, String fullName, String email) {
        if (document == null || document.isBlank()) {
            throw new IllegalArgumentException("document required");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("email invalid");
        }
        OffsetDateTime now = OffsetDateTime.now();
        return new Customer(UUID.randomUUID(), document, fullName, email, now, now);
    }
}
