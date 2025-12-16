package com.example.ticketdemo.payment.application.port.service;

import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.application.port.out.PaymentRepository;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentResult;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final PaymentProcessor paymentProcessor;
  private final PaymentRepository paymentRepository;

  public PaymentService(PaymentProcessor paymentProcessor, PaymentRepository paymentRepository) {
    this.paymentProcessor = paymentProcessor;
    this.paymentRepository = paymentRepository;
  }

  public Payment createPayment(Long reservationId, Long userId, BigDecimal amount) {
    Payment payment = new Payment(reservationId, userId, amount);

    return paymentRepository.save(payment);
  }

  public PaymentResult processPayment(Long paymentId, String paymentMethod) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 결제입니다."));

    PaymentResult result = paymentProcessor.processPayment(payment, paymentMethod);

    if (result.success()) {
      payment.markAsCompleted("PG_PROVIDER", result.transactionId());
    } else {
      payment.markAsFailed();
    }

    paymentRepository.save(payment);

    return result;
  }
}
