package com.atlasbank.customer.adapter.in.web;

import com.atlasbank.customer.domain.model.Customer;
import com.atlasbank.customer.domain.port.in.CreateCustomerUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CreateCustomerRequest request) {
        Customer customer = createCustomerUseCase.execute(
                new CreateCustomerUseCase.CreateCustomerCommand(request.document(), request.fullName(), request.email())
        );
        return ResponseEntity
                .created(URI.create("/api/v1/customers/" + customer.id()))
                .body(CustomerResponse.from(customer));
    }

    public record CreateCustomerRequest(
            @NotBlank @Size(min = 11, max = 14) String document,
            @NotBlank @Size(max = 160) String fullName,
            @NotBlank @Email @Size(max = 160) String email
    ) {}

    public record CustomerResponse(String id, String document, String fullName, String email) {
        static CustomerResponse from(Customer c) {
            return new CustomerResponse(c.id().toString(), c.document(), c.fullName(), c.email());
        }
    }
}
