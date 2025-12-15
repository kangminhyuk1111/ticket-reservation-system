package com.example.ticketdemo.ticket.adapter.out;

import com.example.ticketdemo.ticket.domain.Ticket;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT t FROM Ticket t WHERE t.ticketId = :ticketId")
  Optional<Ticket> findByIdWithLock(@Param("ticketId") Long ticketId);
}
