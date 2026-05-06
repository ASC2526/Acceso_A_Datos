package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<List<Reservation>> getReservationsByBook(@PathVariable String isbn) {
        return ResponseEntity.ok(reservationService.getReservationsByBook(isbn));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestParam String userId,
            @RequestParam String isbn) {

        Reservation reservation = reservationService.createReservation(userId, isbn);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }
}
