package com.example.ticketdemo.ticket.application.service;

import com.example.ticketdemo.ticket.application.port.out.TicketRepository;
import com.example.ticketdemo.ticket.domain.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket findTicketByTicketId(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("존재하지 않는 티켓."));
    }

    public Ticket findTicketByTicketIdWithLock(Long ticketId) {
        return ticketRepository.findByIdWithLock(ticketId).orElseThrow(() -> new RuntimeException("존재하지 않는 티켓."));
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
