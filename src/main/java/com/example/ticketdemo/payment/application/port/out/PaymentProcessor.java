package com.example.ticketdemo.payment.application.port.out;

import com.example.ticketdemo.payment.domain.Payment;

import java.math.BigDecimal;

public interface PaymentProcessor {
    Payment processPayment(String paymentMethod, BigDecimal price);
    void refund(Long paymentId);
}
