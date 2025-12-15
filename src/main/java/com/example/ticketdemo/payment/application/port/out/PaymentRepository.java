package com.example.ticketdemo.payment.application.port.out;

import com.example.ticketdemo.payment.domain.Payment;

public interface PaymentRepository {
  Payment save(Payment payment);
}
