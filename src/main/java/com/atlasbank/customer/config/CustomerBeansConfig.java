package com.atlasbank.customer.config;

import com.atlasbank.customer.application.CreateCustomerService;
import com.atlasbank.customer.domain.port.in.CreateCustomerUseCase;
import com.atlasbank.customer.domain.port.out.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerBeansConfig {

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepository customerRepository) {
        return new CreateCustomerService(customerRepository);
    }
}
