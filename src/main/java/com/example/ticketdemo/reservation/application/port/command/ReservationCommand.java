package com.example.ticketdemo.reservation.application.port.command;

public record ReservationCommand(
        Long userId,
        Long ticketId,
        Long buyingAmount
) {
}
