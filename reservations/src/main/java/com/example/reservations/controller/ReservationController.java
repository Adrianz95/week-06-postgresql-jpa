package com.example.reservations.controller;

import com.example.reservations.dto.reservation.ReservationRequest;
import com.example.reservations.dto.reservation.ReservationResponse;
import com.example.reservations.model.Reservation;
import com.example.reservations.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservations = reservationService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return ResponseEntity.ok(toResponse(reservation));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(
            @Valid @RequestBody ReservationRequest request) {

        Reservation reservation = reservationService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request) {

        Reservation reservation = reservationService.update(id, request);

        return ResponseEntity.ok(toResponse(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                reservation.getUser().getId(),
                reservation.getRoom().getId()
        );
    }
}