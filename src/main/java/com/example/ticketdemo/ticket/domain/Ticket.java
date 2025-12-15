package com.example.ticketdemo.ticket.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @Column(nullable = true)
    private Long reservedByUserId;

    public Ticket() {
    }

    public Ticket(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.ticketStatus = TicketStatus.AVAILABLE;
        this.reservedByUserId = null;
    }

    public Ticket(Long ticketId, String name, BigDecimal price) {
        this.ticketId = ticketId;
        this.name = name;
        this.price = price;
        this.ticketStatus = TicketStatus.AVAILABLE;
        this.reservedByUserId = null;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void reserve(Long userId) {
        if (this.ticketStatus != TicketStatus.AVAILABLE) {
            throw new RuntimeException("이미 예약된 티켓입니다. 현재 상태: " + this.ticketStatus);
        }

        this.ticketStatus = TicketStatus.RESERVED;

        this.reservedByUserId = userId;
    }

    public void cancel() {
        if (this.ticketStatus != TicketStatus.RESERVED) {
            throw new RuntimeException("예약된 티켓만 취소할 수 있습니다. 현재 상태: " + this.ticketStatus);
        }

        this.ticketStatus = TicketStatus.AVAILABLE;
        this.reservedByUserId = null;
    }
}
