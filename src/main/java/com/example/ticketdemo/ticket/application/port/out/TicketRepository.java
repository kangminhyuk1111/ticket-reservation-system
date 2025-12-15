package com.example.ticketdemo.ticket.application.port.out;

import com.example.ticketdemo.ticket.domain.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(Long ticketId);
    Optional<Ticket> findByIdWithLock(Long ticketId);
}
