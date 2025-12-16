package com.example.ticketdemo.reservation.application.service;

import com.example.ticketdemo.reservation.application.port.out.ReservationRepository;
import com.example.ticketdemo.reservation.domain.Reservation;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ReservationPolicyValidator {

  private final ReservationRepository reservationRepository;

  public ReservationPolicyValidator(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public void validateNotAlreadyReserved(Long userId, Long ticketId) {
    Optional<Reservation> reservation = reservationRepository.findByUserIdAndTicketId(userId,
        ticketId);

    if (reservation.isPresent()) {
      throw new RuntimeException("예약이 이미 존재합니다.");
    }
  }
}
