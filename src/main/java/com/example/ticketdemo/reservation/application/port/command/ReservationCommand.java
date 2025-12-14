package com.example.ticketdemo.reservation.application.port.command;

// String인 이유: paymentMethod는 payment의 영역이기 때문에 영역 침범이 일어남.
public record ReservationCommand(
        Long userId,
        Long ticketId,
        String paymentMethod
) {
}
