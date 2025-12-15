package com.example.ticketdemo.payment.application.port.out;

import com.example.ticketdemo.payment.domain.PaymentResult;

public interface ExternalPaymentGateway {
  PaymentResult requestPayment(Long price, String paymentType);
  PaymentResult requestRefund(Long paymentId);
}
