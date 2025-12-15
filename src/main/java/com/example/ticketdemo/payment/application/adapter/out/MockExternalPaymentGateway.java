package com.example.ticketdemo.payment.application.adapter.out;

import com.example.ticketdemo.payment.application.port.out.ExternalPaymentGateway;
import com.example.ticketdemo.payment.domain.PaymentResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class MockExternalPaymentGateway implements ExternalPaymentGateway {

    @Override
    public PaymentResult requestPayment(Long price, String paymentType) {
        // 실제로는 외부 API 호출
        // 여기서는 mock으로 항상 성공 반환

        String transactionId = UUID.randomUUID().toString();
        return new PaymentResult(true, transactionId, "결제 성공");
    }

    @Override
    public PaymentResult requestRefund(Long paymentId) {
        // 실제로는 외부 API 호출
        // 여기서는 mock으로 항상 성공 반환

        return new PaymentResult(true, paymentId.toString(), "환불 성공");
    }
}
