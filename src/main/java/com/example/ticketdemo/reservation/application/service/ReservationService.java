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
public class ReservationService implements ReservationTicketUseCase {

  private final ReservationRepository reservationRepository;
  private final TicketService ticketService;
  private final PaymentService paymentService;

  public ReservationService(ReservationRepository reservationRepository, TicketService ticketService, PaymentService paymentService) {
    this.reservationRepository = reservationRepository;
    this.ticketService = ticketService;
    this.paymentService = paymentService;
  }

  @Override
  @Transactional // 원자적 연산 확보
  public Reservation reservationTicket(ReservationCommand command) {
    // 1. 티켓 정보 조회
    // 비관락으로 해당 티켓에 접근 제한
    Ticket ticket = ticketService.findTicketByTicketIdWithLock(command.ticketId());

    // 2. 결제 정보 생성
    Payment payment = paymentService.createPendingPayment(
        command.userId(),
        ticket.getTicketId(),
        ticket.getPrice(),
        command.paymentMethod()
    );

    // 3. 티켓 상태 변경
    // TODO: command.userId() 가 아니라 userService.findByUserId(command.userId())를 통해 찾아낸 user의 정보로 변경되어야 함
    ticket.reserve(command.userId());
    ticketService.save(ticket);

    // 4. 예약 정보 저장
    Reservation reservation = new Reservation(
        command.userId(),
        ticket.getTicketId(),
        payment.getPaymentId()
    );
    reservationRepository.save(reservation);

    try {
      // 5. 실제 결제 처리
      paymentService.processPayment(payment);

      return reservation;

    } catch (Exception e) {
      // 보상 트랜잭션 (Compensation Transaction): 이미 수행된 작업들을 명시적으로 되돌림
      // 5-1. 티켓 예약 취소 (RESERVED -> AVAILABLE)
      ticket.cancel();
      ticketService.save(ticket);

      // 5-2. 예약 정보 삭제
      reservationRepository.deleteById(reservation.getReservationId());

      // 5-3. 결제 상태를 FAILED로 기록 (이력 추적용)
      paymentService.markPaymentFailed(payment);

      throw new RuntimeException("결제 처리 실패: " + e.getMessage(), e);
    }
  }
}
