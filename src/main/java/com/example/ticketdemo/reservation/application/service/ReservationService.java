package com.example.ticketdemo.reservation.application.service;

import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
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
    private final PaymentProcessor paymentProcessor;

    public ReservationService(ReservationRepository reservationRepository, TicketService ticketService, PaymentProcessor paymentProcessor) {
        this.reservationRepository = reservationRepository;
        this.ticketService = ticketService;
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    @Transactional
    public Reservation reservationTicket(ReservationCommand command) {
        // 1. 사용자, 티켓 정보 조회
        // Ticket 조회 실패처리 -> Ticket 도메인의 역할
        Ticket ticket = ticketService.findTicketByTicketId(command.ticketId());

        // 2. 결제 프로세스
        // 결제 실패 처리 -> 결제 도메인의 역할
        Payment payment = paymentProcessor.processPayment(command.paymentMethod(), ticket.getPrice());

        // 3. 티켓 상태 변경
        // TODO: command.userId() 가 아니라 command.userId()를 통해 찾아낸 user의 정보로 변경되어야 함 -> 지금 굳이 안해도 될 것 같아서..
        ticket.reserve(command.userId());
        ticketService.save(ticket);

        // 4. 예약 정보 저장
        Reservation reservation = new Reservation(
                command.userId(),
                ticket.getTicketId(),
                payment.getPaymentId()
        );
        reservationRepository.save(reservation);

        // 5. 예약 정보 반환
        return reservation;
    }
}
