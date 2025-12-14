package com.example.ticketdemo.reservation.application.port.in;

import com.example.ticketdemo.reservation.application.port.command.ReservationCommand;
import com.example.ticketdemo.reservation.domain.Reservation;

public interface ReservationTicketUseCase {
    Reservation reservationTicket(ReservationCommand command);
}
