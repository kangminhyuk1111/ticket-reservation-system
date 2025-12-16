package com.example.ticketdemo.payment.application.adapter.out;

import com.example.ticketdemo.payment.application.port.out.ExternalPaymentGateway;
import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.domain.Payment;
import com.example.ticketdemo.payment.domain.PaymentResult;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayAdapter implements PaymentProcessor {

  private final ExternalPaymentGateway paymentGateway;

  public PaymentGatewayAdapter(ExternalPaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  @Override
  public PaymentResult processPayment(Payment payment, String paymentMethod) {
    // 외부 PG사 결제 Gateway 호출
    // 결제 결과를 그대로 반환 (성공/실패 모두)
    // 예외를 던지지 않고 결과 객체로 반환
    return paymentGateway.requestPayment(
        payment.getAmount().longValue(),
        paymentMethod
    );
  }
}
