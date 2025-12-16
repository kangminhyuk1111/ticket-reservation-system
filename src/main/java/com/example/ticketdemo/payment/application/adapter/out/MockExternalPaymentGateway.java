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
        // 실제 PG사 연동 시: 토스페이먼츠, 나이스페이, KG이니시스 등의 API 호출
        // 현재는 Mock으로 간단한 검증만 수행

        // 결제 금액 검증
        if (price == null || price <= 0) {
            return new PaymentResult(false, null, "결제 금액이 유효하지 않습니다.");
        }

        // 결제 수단 검증
        if (paymentType == null || paymentType.isBlank()) {
            return new PaymentResult(false, null, "결제 수단이 유효하지 않습니다.");
        }

        // 모의 결제 성공 처리
        String transactionId = "TXN-" + UUID.randomUUID().toString();
        return new PaymentResult(true, transactionId, "결제 성공");
    }

    @Override
    public PaymentResult requestRefund(Long paymentId) {
        // 실제 PG사 연동 시: 환불 API 호출
        // 현재는 Mock으로 항상 성공 반환

        if (paymentId == null) {
            return new PaymentResult(false, null, "결제 ID가 유효하지 않습니다.");
        }

        String refundId = "REFUND-" + UUID.randomUUID().toString();
        return new PaymentResult(true, refundId, "환불 성공");
    }
}
