package com.example.ticketdemo.payment.domain;

public record PaymentResult(boolean success, String transactionId, String message) {
}
