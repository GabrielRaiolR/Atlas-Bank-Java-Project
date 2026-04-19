package com.atlasbank.account.adapter.in.web;

import com.atlasbank.account.domain.model.Account;
import com.atlasbank.account.domain.port.in.GetAccountUseCase;
import com.atlasbank.account.domain.port.in.OpenAccountUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final OpenAccountUseCase openAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;


    @PostMapping
    public ResponseEntity<AccountResponse> open(@RequestBody @Valid OpenAccountRequest request) {
        Account account = openAccountUseCase.execute(new OpenAccountUseCase.OpenAccountCommand(request.customerId()));
        return ResponseEntity
                .created(URI.create("/api/v1/accounts/" + account.getId()))
                .body(AccountResponse.from(account));
    }

    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable UUID id) {
        return AccountResponse.from(getAccountUseCase.byId(id));
    }

    public record OpenAccountRequest(@NotNull UUID customerId) {}

    public record AccountResponse(String id, String number, BigDecimal balance, String status) {
        static AccountResponse from(Account a) {
            return new AccountResponse(a.getId().toString(), a.getNumber(),
                    a.getBalance(), a.getStatus().name());
        }
    }
}
