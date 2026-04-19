package com.atlasbank.customer.adapter.out.persistence;

import com.atlasbank.customer.domain.model.Customer;
import com.atlasbank.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepository {

    private final SpringDataCustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = toEntity(customer);
        CustomerJpaEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByDocument(String document) {
        return repository.findByDocument(document).map(this::toDomain);
    }

    @Override
    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }

    private Customer toDomain(CustomerJpaEntity e) {
        return new Customer(e.getId(), e.getDocument(), e.getFullName(),
                e.getEmail(), e.getCreatedAt(), e.getUpdatedAt());
    }

    private CustomerJpaEntity toEntity(Customer c) {
        return CustomerJpaEntity.builder()
                .id(c.id())
                .document(c.document())
                .fullName(c.fullName())
                .email(c.email())
                .createdAt(c.createdAt())
                .updatedAt(c.updatedAt())
                .build();
    }
}
