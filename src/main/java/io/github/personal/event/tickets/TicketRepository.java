package io.github.personal.event.tickets;

import io.github.personal.event.tickets.vo.TicketId;
import io.github.personal.event.tickets.vo.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface TicketRepository extends MongoRepository<Ticket, TicketId> {
    Optional<Ticket> findByUserId(UserId id);
}
