package com.example.ticketdemo.reservation.application.service;

import com.example.ticketdemo.payment.application.port.service.PaymentService;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.reservation.application.port.command.ReservationCommand;
import com.example.ticketdemo.reservation.application.port.in.ReservationTicketUseCase;
import com.example.ticketdemo.reservation.application.port.out.ReservationRepository;
import com.example.ticketdemo.reservation.domain.Reservation;
import com.example.ticketdemo.ticket.application.service.TicketService;
import com.example.ticketdemo.ticket.domain.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  private final ReservationPolicyValidator validator;
  private final ReservationRepository reservationRepository;

  public ReservationService(ReservationPolicyValidator validator, ReservationRepository reservationRepository) {
    this.validator = validator;
    this.reservationRepository = reservationRepository;
  }

  public Reservation createReservation(Long userId, Long ticketId, Long buyingAmount) {
    validator.validateNotAlreadyReserved(userId, ticketId);

    Reservation reservation = new Reservation(userId, ticketId, buyingAmount);

    return reservationRepository.save(reservation);
  }

  public Reservation findByReservationId(Long reservationId) {
    return reservationRepository.findByReservationId(reservationId).orElseThrow(() -> new RuntimeException("존재하지 않는 예약 정보."));
  }
}
