package com.example.ticketdemo.payment.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column
    private String pgProvider;

    @Column
    private String pgTransactionId;

    @Column
    private LocalDateTime paidAt;

    public Payment() {
    }

    public Payment(Long reservationId, Long userId, BigDecimal amount) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getUserId() {
        return userId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void markAsCompleted(String pgProvider, String pgTransactionId) {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태에서만 완료 처리할 수 있습니다.");
        }
        this.paymentStatus = PaymentStatus.COMPLETE;
        this.pgProvider = pgProvider;
        this.pgTransactionId = pgTransactionId;
        this.paidAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태에서만 실패 처리할 수 있습니다.");
        }
        this.paymentStatus = PaymentStatus.FAILED;
    }
}
