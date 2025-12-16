package com.example.ticketdemo.payment.application.port.in;

import com.example.ticketdemo.payment.application.port.command.PaymentCommand;
import com.example.ticketdemo.payment.domain.Payment;

public interface ProcessPaymentUseCase {
  Payment processPayment(PaymentCommand command);
}
