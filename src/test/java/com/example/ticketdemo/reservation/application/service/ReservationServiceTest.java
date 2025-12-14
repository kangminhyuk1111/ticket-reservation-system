package com.example.ticketdemo.reservation.application.service;

import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.reservation.application.port.command.ReservationCommand;
import com.example.ticketdemo.reservation.application.port.out.ReservationRepository;
import com.example.ticketdemo.reservation.domain.Reservation;
import com.example.ticketdemo.ticket.application.service.TicketService;
import com.example.ticketdemo.ticket.domain.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TicketService ticketService;

    @Mock
    private PaymentProcessor paymentProcessor;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(
                reservationRepository,
                ticketService,
                paymentProcessor
        );
    }

    @Test
    void 티켓_예약_테스트() {
        ReservationCommand command = new ReservationCommand(1L, 100L, "CREDIT-CARD");

        Ticket ticket = new Ticket(100L, "ticket-123", BigDecimal.valueOf(10000L));
        Payment payment = new Payment(1L, BigDecimal.valueOf(10000L));

        when(ticketService.findTicketByTicketId(100L)).thenReturn(ticket);
        when(paymentProcessor.processPayment(command.paymentMethod(), BigDecimal.valueOf(10000L))).thenReturn(payment);

        Reservation reservationResult = reservationService.reservationTicket(command);

        assertAll(() -> {
            assertThat(reservationResult).isNotNull();
            assertThat(reservationResult.getUserId()).isEqualTo(1L);
            assertThat(reservationResult.getTicketId()).isEqualTo(100L);
            assertThat(reservationResult.getPaymentId()).isEqualTo(1L);
        });
    }
}