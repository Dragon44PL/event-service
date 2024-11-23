package io.github.personal.event.tickets.command;

import io.github.personal.event.tickets.vo.EventId;
import io.github.personal.event.tickets.vo.UserId;

public record CreateTicketCommand(UserId userId, EventId eventId) { }
