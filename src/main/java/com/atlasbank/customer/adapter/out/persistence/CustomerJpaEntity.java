package com.atlasbank.customer.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 14)
    private String document;

    @Column(nullable = false, length = 160, name = "full_name")
    private String fullName;

    @Column(nullable = false,unique = true, length = 160)
    private String email;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
