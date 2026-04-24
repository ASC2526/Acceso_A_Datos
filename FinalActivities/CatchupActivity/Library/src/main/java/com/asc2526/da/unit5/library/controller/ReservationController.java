package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/reservations/book/{isbn}")
    public List<Reservation> getReservationsByBook(@PathVariable String isbn) {
        return reservationService.getReservationsByBook(isbn);
    }

    @GetMapping("/reservations/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable String userId) {
        return reservationService.getReservationsByUser(userId);
    }
}
