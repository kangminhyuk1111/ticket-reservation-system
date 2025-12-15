package com.example.ticketdemo.payment.application.port.service;

import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.application.port.out.PaymentRepository;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentMethod;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

  private final PaymentProcessor paymentProcessor;
  private final PaymentRepository paymentRepository;

  public PaymentService(PaymentProcessor paymentProcessor, PaymentRepository paymentRepository) {
    this.paymentProcessor = paymentProcessor;
    this.paymentRepository = paymentRepository;
  }

  public Payment createPendingPayment(Long userId, Long ticketId, BigDecimal price, String paymentMethod) {
    PaymentMethod method = PaymentMethod.valueOf(paymentMethod);

    Payment payment = Payment.createPending(userId, ticketId, price, method);

    return paymentRepository.save(payment);
  }

  @Transactional
  public void processPayment(Payment payment) {
    try {
      paymentProcessor.processPayment(payment.getPaymentMethod().toString(), payment.getAmount());

      payment.markAsCompleted();
      paymentRepository.save(payment);

    } catch (Exception e) {
      payment.markAsFailed();
      paymentRepository.save(payment);
      throw e;
    }
  }

  @Transactional
  public void refundPayment(Payment payment) {
    try {
      paymentProcessor.refund(payment.getPaymentId());

      payment.markAsRefunded();
      paymentRepository.save(payment);

    } catch (Exception e) {
      throw new RuntimeException("환불 처리 실패", e);
    }
  }
}
