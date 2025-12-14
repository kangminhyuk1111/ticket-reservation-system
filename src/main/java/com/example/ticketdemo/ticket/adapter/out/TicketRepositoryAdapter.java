package com.example.ticketdemo.ticket.adapter.out;

import com.example.ticketdemo.ticket.application.port.out.TicketRepository;
import com.example.ticketdemo.ticket.domain.Ticket;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TicketRepositoryAdapter implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    public TicketRepositoryAdapter(JpaTicketRepository jpaTicketRepository) {
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return jpaTicketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long ticketId) {
        return jpaTicketRepository.findById(ticketId);
    }
}
