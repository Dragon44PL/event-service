package io.github.personal.event.tickets.generator;

import io.github.personal.event.tickets.dto.TicketDto;

public interface TicketGenerator {
    void generate(TicketDto ticket);
}
