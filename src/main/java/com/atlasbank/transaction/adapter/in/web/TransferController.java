package com.atlasbank.transaction.adapter.in.web;

import com.atlasbank.transaction.domain.model.TransactionRecord;
import com.atlasbank.transaction.domain.port.in.TransferUseCase;
import com.atlasbank.transaction.domain.port.in.TransferUseCase.TransferCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferUseCase transferUseCase;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(
            @RequestBody @Valid TransferRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ) {
        TransactionRecord result = transferUseCase.execute(new TransferCommand(
                request.fromAccountId(),
                request.toAccountId(),
                request.amount(),
                request.description(),
                idempotencyKey
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(TransferResponse.from(result));
    }

    public record TransferRequest(
            @NotNull UUID fromAccountId,
            @NotNull UUID toAccountId,
            @NotNull @DecimalMin("0.01") BigDecimal amount,
            @Size(max = 255) String description
    ) {}

    public record TransferResponse(String id, String status, BigDecimal amount) {
        static TransferResponse from(TransactionRecord r) {
            return new TransferResponse(r.id().toString(), r.status().name(), r.amount());
        }
    }
}