package com.example.ticketdemo.payment.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(Long paymentId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }

    public Payment(Long userId, Long ticketId, BigDecimal amount, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public static Payment createPending(Long userId, Long ticketId, BigDecimal amount, PaymentMethod paymentMethod) {
        return new Payment(
            userId, ticketId, amount, paymentMethod
        );
    }

    public Payment(BigDecimal amount) {
        this.amount = amount;
    }

    public void markAsCompleted() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태인 결제만 완료 처리할 수 있습니다");
        }
        this.status = PaymentStatus.COMPLETE;
    }

    public void markAsRefunded() {
        if (this.status != PaymentStatus.COMPLETE) {
            throw new IllegalStateException("COMPLETE 상태인 결제만 환불 처리할 수 있습니다");
        }
        this.status = PaymentStatus.REFUNDED;
    }

    public void markAsFailed() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태인 결제만 결제 진행 가능합니다.");
        }
        this.status = PaymentStatus.FAILED;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
