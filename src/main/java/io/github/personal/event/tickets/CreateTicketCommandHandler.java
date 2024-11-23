package io.github.personal.event.tickets;

import io.github.personal.event.tickets.command.CreateTicketCommand;
import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.generator.TicketGenerator;
import io.github.personal.event.tickets.vo.TicketId;
import io.github.personal.event.tickets.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTicketCommandHandler {

    private final TicketGenerator ticketGenerator;
    private final TicketRepository ticketRepository;

    @Transactional
    public TicketId handle(final CreateTicketCommand createTicketCommand) {
        log.info("Checking CreateTicketCommand: {}", createTicketCommand);
        checkIfTicketNotExists(createTicketCommand.userId());

        final Ticket ticket = Ticket.create(createTicketCommand.userId(), createTicketCommand.eventId());
        ticketRepository.save(ticket);
        generateTicket(ticket);
        log.info("Created ticket: {}", ticket);
        return ticket.getId();
    }

    private void checkIfTicketNotExists(final UserId userId) {
        ticketRepository.findByUserId(userId)
            .ifPresent(_ -> { throw new TicketException("Ticket already exists for userId = " + userId); });
    }

    private void generateTicket(Ticket ticket) {
        log.info("Generating ticket: {}", ticket);
        final var ticketDto = new TicketDto(ticket.getId(), ticket.getUserId(), ticket.getEventId());
        ticketGenerator.generate(ticketDto);
    }
}
