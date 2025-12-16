package com.example.ticketdemo.payment.application.port.out;

import com.example.ticketdemo.payment.domain.Payment;
import java.util.Optional;

public interface PaymentRepository {
  Payment save(Payment payment);
  Optional<Payment> findById(Long paymentId);
}
