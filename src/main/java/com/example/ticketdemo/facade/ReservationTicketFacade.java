package com.example.ticketdemo.facade;

import com.example.ticketdemo.reservation.application.port.command.ReservationCommand;
import com.example.ticketdemo.reservation.application.port.in.ReservationTicketUseCase;
import com.example.ticketdemo.reservation.application.service.ReservationService;
import com.example.ticketdemo.reservation.domain.Reservation;
import com.example.ticketdemo.ticket.application.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationTicketFacade implements ReservationTicketUseCase {

  private final ReservationService reservationService;
  private final TicketService ticketService;

  public ReservationTicketFacade(ReservationService reservationService, TicketService ticketService) {
    this.reservationService = reservationService;
    this.ticketService = ticketService;
  }

  @Override
  @Transactional
  public Reservation reservationTicket(ReservationCommand command) {
    // 0: 중복 검증은 createReservation에서 직접 생성할 때 진행
    // 1: 재고 차감
    ticketService.decrementStock(command.ticketId(), command.buyingAmount());

    // 2: 예약 생성
    return reservationService.createReservation(command.userId(), command.ticketId(), command.buyingAmount());
  }
}
