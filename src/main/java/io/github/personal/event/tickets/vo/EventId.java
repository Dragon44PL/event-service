package io.github.personal.event.tickets.vo;

import java.util.UUID;

public record EventId(UUID userId) {
    public static EventId create() {
        return new EventId(UUID.randomUUID());
    }
}
