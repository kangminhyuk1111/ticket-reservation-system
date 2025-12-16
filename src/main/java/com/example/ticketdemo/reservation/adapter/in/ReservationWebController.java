package com.example.ticketdemo.reservation.adapter.in;

import com.example.ticketdemo.reservation.application.port.command.ReservationCommand;
import com.example.ticketdemo.reservation.adapter.in.dto.ReservationRequest;
import com.example.ticketdemo.reservation.application.port.in.ReservationTicketUseCase;
import com.example.ticketdemo.reservation.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationWebController {

    private final ReservationTicketUseCase reservationTicketUseCase;

    public ReservationWebController(ReservationTicketUseCase reservationTicketUseCase) {
        this.reservationTicketUseCase = reservationTicketUseCase;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserveTicket(@RequestBody ReservationRequest request) {
        // TODO: Entity return 대신 Response 객체로 변경 -> 도메인 객체는 외부에 노출하지 않고 필요한 데이터만 노출하기 위해
        Reservation reservationResult = reservationTicketUseCase.reservationTicket(
                new ReservationCommand(
                        request.userId(),
                        request.ticketId(),
                        request.buyingAmount()
                )
        );

        return ResponseEntity.status(201).body(reservationResult);
    }
}
