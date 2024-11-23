package io.github.personal.event.api.dto;

import io.github.personal.event.tickets.command.CreateTicketCommand;
import io.github.personal.event.tickets.vo.EventId;
import io.github.personal.event.tickets.vo.UserId;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateTicketDto(@NotBlank UUID userId, @NotBlank UUID eventId) {
    public CreateTicketCommand toCommand() {
        return new CreateTicketCommand(new UserId(userId), new EventId(eventId));
    }
}
