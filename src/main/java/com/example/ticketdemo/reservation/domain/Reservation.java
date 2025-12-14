package com.example.ticketdemo.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Long userId;

    private Long ticketId;

    private Long paymentId;

    public Reservation() {
    }

    public Reservation(Long userId, Long ticketId, Long paymentId) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.paymentId = paymentId;
    }

    public Reservation(Long reservationId, Long userId, Long ticketId, Long paymentId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.paymentId = paymentId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
