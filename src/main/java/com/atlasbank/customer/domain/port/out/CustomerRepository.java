package com.atlasbank.customer.domain.port.out;

import com.atlasbank.customer.domain.model.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByDocument(String document);
    boolean existsByDocument(String document);
}
