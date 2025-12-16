package com.example.ticketdemo.ticket.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Long totalQuantity;

    @Column(nullable = false)
    private Long remainingQuantity;

    public Ticket() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public Long getRemainingQuantity() {
        return remainingQuantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

  public void deduct(Long buyingAmount) {
    if (buyingAmount <= 0) {
      throw new RuntimeException("구매 수량은 0보다 커야 합니다.");
    }

    if (remainingQuantity < buyingAmount) {
      throw new RuntimeException("재고가 부족합니다. (요청: " + buyingAmount + ", 재고: " + remainingQuantity + ")");
    }

    this.remainingQuantity -= buyingAmount;
  }

  public void earn(Long returningAmount) {
    if (returningAmount <= 0) {
      throw new RuntimeException("복구 수량은 0보다 커야 합니다.");
    }

    if (remainingQuantity + returningAmount > totalQuantity) {
      throw new RuntimeException("총 수량을 초과할 수 없습니다.");
    }

    this.remainingQuantity += returningAmount;
  }
}
