package com.atlasbank.customer.application;

import com.atlasbank.common.exception.ConflictException;
import com.atlasbank.customer.domain.model.Customer;
import com.atlasbank.customer.domain.port.in.CreateCustomerUseCase;
import com.atlasbank.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Customer execute(CreateCustomerCommand command) {
        if (customerRepository.existsByDocument(command.document())) {
            throw new ConflictException("Customer already exists for documents");
        }
        Customer customer = Customer.create(command.document(), command.fullName(), command.email());
        return customerRepository.save(customer);
    }
}
