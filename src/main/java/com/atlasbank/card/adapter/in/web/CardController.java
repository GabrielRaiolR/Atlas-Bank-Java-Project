package com.atlasbank.card.adapter.in.web;

import com.atlasbank.card.domain.model.Card;
import com.atlasbank.card.domain.port.in.GetCardUseCase;
import com.atlasbank.card.domain.port.in.IssueCardUseCase;
import com.atlasbank.card.domain.port.in.IssueCardUseCase.IssueCardCommand;
import com.atlasbank.card.domain.port.in.PurchaseUseCase;
import com.atlasbank.card.domain.port.in.PurchaseUseCase.PurchaseCommand;
import com.atlasbank.card.domain.port.in.PurchaseUseCase.PurchaseResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final IssueCardUseCase issueCardUseCase;
    private final GetCardUseCase getCardUseCase;
    private final PurchaseUseCase purchaseUseCase;

    @PostMapping
    public ResponseEntity<CardResponse> issue(@RequestBody @Valid IssueCardRequest request) {
        Card card = issueCardUseCase.execute(new IssueCardCommand(
                request.accountId(), request.creditLimit(), request.panLastFour()
        ));
        return ResponseEntity.created(URI.create("/api/v1/cards/" + card.getId())).body(CardResponse.from(card));
    }

    @GetMapping("/{id}")
    public CardResponse get(@PathVariable UUID id) {
        return CardResponse.from(getCardUseCase.byId(id));
    }

    @PostMapping("/{id}/purchases")
    public ResponseEntity<PurchaseResponse> purchase(
            @PathVariable UUID id,
            @RequestBody @Valid PurchaseRequest request
    ) {
        PurchaseResult r = purchaseUseCase.execute(new PurchaseCommand(
                id, request.amount(), request.merchant()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseResponse.from(r));
    }


    public record IssueCardRequest(
            @NotNull UUID accountId,
            @NotNull @DecimalMin("0.01") BigDecimal creditLimit,
            @NotNull @Pattern(regexp = "\\d{4}") String panLastFour
    ) {}

    public record PurchaseRequest(
            @NotNull @DecimalMin("0.01") BigDecimal amount,
            @NotBlank @Size(max= 80) String merchant
    ) {}

    public record CardResponse (
            String id,
            String accountId,
            BigDecimal creditLimit,
            BigDecimal availableLimit,
            String status,
            String maskedPan
    ) {
        static CardResponse from(Card c) {
            return new CardResponse(
                    c.getId().toString(),
                    c.getAccountId().toString(),
                    c.getCreditLimit(),
                    c.getAvailableLimit(),
                    c.getStatus().name(),
                    "**** **** **** " + c.getPanLastFour()
            );
        }
    }

    public record PurchaseResponse(
            String cardId,
            BigDecimal amount,
            BigDecimal availableLimit,
            OffsetDateTime occurredAt
    ) {
        static PurchaseResponse from(PurchaseResult r) {
            return new PurchaseResponse(r.cardId().toString(), r.amount(), r.availableLimit(), r.occurredAt());
        }
    }
}
