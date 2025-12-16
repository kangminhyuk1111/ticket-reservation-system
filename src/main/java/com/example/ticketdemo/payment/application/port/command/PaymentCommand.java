package com.example.ticketdemo.payment.application.port.command;

public record PaymentCommand(
    Long reservationId,
    String paymentMethod
) {
}
