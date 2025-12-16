package com.example.ticketdemo.ticket.application.service;

import com.example.ticketdemo.ticket.application.port.out.TicketRepository;
import com.example.ticketdemo.ticket.domain.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Transactional
  public void decrementStock(Long ticketId, Long buyingAmount) {
    Ticket ticket = ticketRepository.findByIdWithLock(ticketId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓."));

    ticket.deduct(buyingAmount);
  }
  @Transactional
  public void incrementStock(Long ticketId, Long returningAmount) {
    Ticket ticket = ticketRepository.findByIdWithLock(ticketId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓."));

    ticket.earn(returningAmount);
  }
}
