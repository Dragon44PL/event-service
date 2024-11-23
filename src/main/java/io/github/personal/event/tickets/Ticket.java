package io.github.personal.event.tickets;

import io.github.personal.event.tickets.vo.EventId;
import io.github.personal.event.tickets.vo.TicketId;
import io.github.personal.event.tickets.vo.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Ticket {

    @Id
    private TicketId id;
    private UserId userId;
    private EventId eventId;

    static Ticket create(UserId userId, EventId eventId) {
        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(eventId, "EventId cannot be null");
        return new Ticket(TicketId.create(), userId, eventId);
    }
}
