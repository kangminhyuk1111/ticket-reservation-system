package com.example.ticketdemo.ticket.adapter.out;

import com.example.ticketdemo.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {
}
