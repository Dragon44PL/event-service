package io.github.personal.event.tickets.dto;

import io.github.personal.event.tickets.vo.EventId;
import io.github.personal.event.tickets.vo.TicketId;
import io.github.personal.event.tickets.vo.UserId;

public record TicketDto(TicketId id, UserId userId, EventId eventId) { }
