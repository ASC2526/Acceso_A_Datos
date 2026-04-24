package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.BookNotFoundException;
import com.asc2526.da.unit5.library.exception.UserNotFoundException;
import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.ReservationRepository;
import com.asc2526.da.unit5.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByBook(String isbn) {

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return reservationRepository.findByBook(isbn);
    }

    public List<Reservation> getReservationsByUser(String userId) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return reservationRepository.findByBorrower(userId);
    }
}
