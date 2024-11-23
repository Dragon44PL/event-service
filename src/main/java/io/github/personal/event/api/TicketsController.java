package io.github.personal.event.api;

import io.github.personal.event.api.dto.CreateTicketDto;
import io.github.personal.event.api.dto.TicketCreatedDto;
import io.github.personal.event.tickets.CreateTicketCommandHandler;
import io.github.personal.event.tickets.TicketReadService;
import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.generator.TicketProvider;
import io.github.personal.event.tickets.vo.TicketId;
import io.github.personal.event.tickets.vo.UserId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
class TicketsController {

    private final TicketProvider ticketProvider;
    private final TicketReadService ticketReadService;
    private final CreateTicketCommandHandler createTicketCommandHandler;

    @GetMapping("/api/v1/tickets")
    ResponseEntity<TicketDto> getTicketByUserId(@RequestParam String userId) {
        return ticketReadService.findByUserId(new UserId(UUID.fromString(userId)))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/tickets")
    TicketCreatedDto createTicket(@Valid @RequestBody CreateTicketDto createTicketDto) {
        final var ticketId = createTicketCommandHandler.handle(createTicketDto.toCommand());
        return new TicketCreatedDto(ticketId.id());
    }

    @GetMapping(value = "/api/v1/tickets:qr")
    ResponseEntity<byte[]> getTicketFile(@RequestParam String ticketId) {
        final var qr = ticketProvider.getTicketData(new TicketId(UUID.fromString(ticketId)));
        return ResponseEntity.ok()
                .header("Content-Type", "image/" + qr.type())
                .body(qr.data());
    }
}
