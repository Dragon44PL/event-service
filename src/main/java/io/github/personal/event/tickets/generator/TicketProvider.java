package io.github.personal.event.tickets.generator;

import io.github.personal.event.tickets.generator.dto.QrCode;
import io.github.personal.event.tickets.vo.TicketId;

public interface TicketProvider {
    QrCode getTicketData(TicketId ticketId);
}
