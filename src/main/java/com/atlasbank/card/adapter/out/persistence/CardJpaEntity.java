package com.atlasbank.card.adapter.out.persistence;

import com.atlasbank.card.domain.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardJpaEntity {

    @Id
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "credit_limit", nullable = false, precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @Column(name = "available_limit", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private CardStatus status;

    @Column(name = "pan_last_four", nullable = false, length = 4)
    private String panLastFour;

    @Version
    @Column(nullable = false)
    private long version;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

}
