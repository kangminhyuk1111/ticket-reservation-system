package com.example.ticketdemo.reservation.application.port.out;

import com.example.ticketdemo.reservation.domain.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
}
