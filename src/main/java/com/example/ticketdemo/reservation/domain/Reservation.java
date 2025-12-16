package com.example.ticketdemo.reservation.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "ticketId"})
})
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reservationId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long ticketId;

  @Column(nullable = false)
  private Long quantity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReservationStatus reservationStatus = ReservationStatus.PENDING;

  @Column(nullable = false)
  private LocalDateTime paymentDeadline;

  @Column(nullable = false)
  private LocalDateTime reservedAt;

  @Column
  private LocalDateTime paidAt;

  @Column
  private LocalDateTime cancelledAt;

  public Reservation() {
  }

  public Reservation(Long userId, Long ticketId, Long quantity) {
    this.userId = userId;
    this.ticketId = ticketId;
    this.quantity = quantity;
    this.paymentDeadline = LocalDateTime.now().plusMinutes(15);
    this.reservedAt = LocalDateTime.now();
  }

  public void validatePaymentAvailability() {
    if (!reservationStatus.equals(ReservationStatus.PENDING)) {
      throw new RuntimeException("결제가 대기상태가 아니면 결제할 수 없습니다.");
    }

    if (paymentDeadline.isBefore(LocalDateTime.now())) {
      throw new RuntimeException("결제 기한이 지났습니다.");
    }
  }

  public Long getReservationId() {
    return reservationId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getTicketId() {
    return ticketId;
  }

  public Long getQuantity() {
    return quantity;
  }

  public BigDecimal calculateAmount() {
    return BigDecimal.ZERO;
  }

  public void markAsPaid() {
    if (!reservationStatus.equals(ReservationStatus.PENDING)) {
      throw new RuntimeException("결제가 대기상태가 아니면 결제할 수 없습니다.");
    }

    this.reservationStatus = ReservationStatus.PAID;
  }

  public void markAsCancelled() {
    this.reservationStatus = ReservationStatus.CANCELLED;
  }
}
