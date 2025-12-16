package com.example.ticketdemo.payment.application.port.out;

import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentResult;

public interface PaymentProcessor {
    PaymentResult processPayment(Payment payment, String paymentMethod);
}
