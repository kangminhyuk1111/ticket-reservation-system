package com.example.ticketdemo.reservation.application.port.out;

import com.example.ticketdemo.reservation.domain.Reservation;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    void deleteById(Long reservationId);
    Optional<Reservation> findByUserIdAndTicketId(Long userId, Long ticketId);
    Optional<Reservation> findByReservationId(Long reservationId);
}
