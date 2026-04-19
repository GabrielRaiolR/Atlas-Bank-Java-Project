package com.atlasbank.customer.domain.port.in;

import com.atlasbank.customer.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer execute(CreateCustomerCommand command);

    record CreateCustomerCommand(String document, String fullName, String email) {}
}
