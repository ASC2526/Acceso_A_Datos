package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.BookNotFoundException;
import com.asc2526.da.unit5.library.exception.ReservationAlreadyExistsException;
import com.asc2526.da.unit5.library.exception.UserNotFoundException;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.ReservationRepository;
import com.asc2526.da.unit5.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return reservationRepository.findByBook(book);
    }

    public List<Reservation> getReservationsByUser(String userId) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return reservationRepository.findByBorrower(user);
    }

    public Reservation createReservation(String userId, String isbn) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFined() != null && user.getFined().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("User cannot reserve books while fined until: " + user.getFined());
        }
        if ((user.getEmail() == null || user.getEmail().isBlank()) &&
                (user.getPhone() == null || user.getPhone().isBlank())) {
            throw new IllegalArgumentException("User must have email or phone to make a reservation.");
        }

        Optional<Reservation> optReserve = reservationRepository
                .findReservationByBookAndBorrowerAndLendingNull(book, user);

        if (optReserve.isPresent())
            throw new ReservationAlreadyExistsException(isbn, userId);


        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setBorrower(user);
        reservation.setDate(LocalDate.now());
        reservation.setLending(null);

        return reservationRepository.save(reservation);
    }
}
