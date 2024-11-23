package io.github.personal.event.tickets.vo;

import java.util.UUID;

public record TicketId(UUID id) {
    public static TicketId create() {
        return new TicketId(UUID.randomUUID());
    }
}
