package io.github.personal.event.tickets;

import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class TicketMongoReadService implements TicketReadService {

    private final TicketRepository ticketRepository;

    @Override
    public Optional<TicketDto> findByUserId(UserId userId) {
        return ticketRepository.findByUserId(userId)
                .map(TicketMongoReadService::toDto);
    }

    private static TicketDto toDto(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getUserId(), ticket.getEventId());
    }
}
