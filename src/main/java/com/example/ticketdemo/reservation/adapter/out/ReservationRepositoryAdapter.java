package com.example.ticketdemo.reservation.adapter.out;

import com.example.ticketdemo.reservation.application.port.out.ReservationRepository;
import com.example.ticketdemo.reservation.domain.Reservation;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final JpaReservationRepository jpaReservationRepository;

    public ReservationRepositoryAdapter(JpaReservationRepository jpaReservationRepository) {
        this.jpaReservationRepository = jpaReservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return jpaReservationRepository.save(reservation);
    }

    @Override
    public void deleteById(Long reservationId) {
        jpaReservationRepository.deleteById(reservationId);
    }

    @Override
    public Optional<Reservation> findByUserIdAndTicketId(Long userId, Long ticketId) {
        return jpaReservationRepository.findByUserIdAndTicketId(userId, ticketId);
    }
}
