package io.github.personal.event.tickets.vo;

import java.util.UUID;

public record UserId(UUID id) {
    public UserId create() {
        return new UserId(UUID.randomUUID());
    }
}
