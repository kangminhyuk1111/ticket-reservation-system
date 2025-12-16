package com.example.ticketdemo.reservation.adapter.out;

import com.example.ticketdemo.reservation.domain.Reservation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
  Optional<Reservation> findByUserIdAndTicketId(Long userId, Long ticketId);
}
