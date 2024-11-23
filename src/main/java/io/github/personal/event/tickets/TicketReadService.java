package io.github.personal.event.tickets;

import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.vo.UserId;

import java.util.Optional;

public interface TicketReadService {
    Optional<TicketDto> findByUserId(UserId userId);
}
