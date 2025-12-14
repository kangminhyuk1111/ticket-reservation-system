package com.example.ticketdemo.payment.application.adatper.out;

import com.example.ticketdemo.payment.application.port.out.PaymentProcessor;
import com.example.ticketdemo.payment.domain.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentGatewayAdapter implements PaymentProcessor {

    @Override
    public Payment processPayment(String paymentType, BigDecimal price) {
        // 결제 프로세스 진행
        // 외부 API외의 연결부 수행
        return new Payment(price);
    }
}
