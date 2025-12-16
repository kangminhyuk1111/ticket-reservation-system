package com.example.ticketdemo.facade;

import com.example.ticketdemo.payment.application.port.command.PaymentCommand;
import com.example.ticketdemo.payment.application.port.service.PaymentService;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentResult;
import com.example.ticketdemo.reservation.application.service.ReservationService;
import com.example.ticketdemo.reservation.domain.Reservation;
import com.example.ticketdemo.ticket.application.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentReservationFacade {

  private final PaymentService paymentService;
  private final ReservationService reservationService;
  private final TicketService ticketService;

  public PaymentReservationFacade(PaymentService paymentService, ReservationService reservationService, TicketService ticketService) {
    this.paymentService = paymentService;
    this.reservationService = reservationService;
    this.ticketService = ticketService;
  }

  @Transactional
  public PaymentResult processPayment(PaymentCommand command) {
    // 1. 예약 조회
    Reservation reservation = reservationService.findByReservationId(command.reservationId());

    // 2. 결제 가능 상태 검증
    reservation.validatePaymentAvailability();

    // 3. 결제 처리
    Payment payment = paymentService.createPayment(
        reservation.getReservationId(),
        reservation.getUserId(),
        reservation.calculateAmount()
    );

    // 3-2. 외부 PG 결제 처리 (Payment 상태: PENDING -> COMPLETE/FAILED)
    PaymentResult paymentResult = paymentService.processPayment(
        payment.getPaymentId(),
        command.paymentMethod()
    );

    // 3-3. 결제 결과에 따라 예약 상태 업데이트
    if (paymentResult.success()) {
      reservation.markAsPaid();
    } else {
      ticketService.incrementStock(reservation.getTicketId(), reservation.getQuantity());
      reservation.markAsCancelled();
    }

    return paymentResult;
  }
}
