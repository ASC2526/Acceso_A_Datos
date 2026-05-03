package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.service.ReservationService;
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
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/book/{isbn}")
    public List<Reservation> getReservationsByBook(@PathVariable String isbn) {
        return reservationService.getReservationsByBook(isbn);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable String userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @PostMapping
    public Reservation createReservation(
            @RequestParam String userId,
            @RequestParam String isbn) {

        return reservationService.createReservation(userId, isbn);
    }
}
