package com.example.ticketdemo.reservation.adapter.in.dto;

public record ReservationRequest(
        Long userId,
        Long ticketId,
        Long buyingAmount
) {
}
