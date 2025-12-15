package com.example.ticketdemo.payment.application.adatper.out;

import com.example.ticketdemo.payment.application.port.out.ExternalPaymentGateway;
import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentGatewayAdapter implements PaymentProcessor {

  private final ExternalPaymentGateway paymentGateway;

  public PaymentGatewayAdapter(ExternalPaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  @Override
  public Payment processPayment(String paymentType, BigDecimal price) {
    // 외부 결제 Gateway 호출
    PaymentResult result = paymentGateway.requestPayment(price.longValue(), paymentType);

    if (!result.success()) {
      throw new RuntimeException("결제 실패: " + result.message());
    }

    return new Payment(price);
  }

  @Override
  public void refund(Long paymentId) {
    // 외부 환불 Gateway 호출
    PaymentResult result = paymentGateway.requestRefund(paymentId);

    if (!result.success()) {
      throw new RuntimeException("환불 실패: " + result.message());
    }
  }
}
