package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.*;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.LendingRepository;
import com.asc2526.da.unit5.library.repository.ReservationRepository;
import com.asc2526.da.unit5.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class LendingService {

    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public LendingService(LendingRepository lendingRepository, BookRepository bookRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Lending lendBook(Lending lending) {

        if (lending == null) {
            throw new IllegalArgumentException("Lending cannot be null");
        }

        String isbn = lending.getBook();
        String userId = lending.getBorrower();

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFined() != null && user.getFined().isAfter(LocalDate.now())) {
            throw new UserFinedException(userId);
        }

        Optional<Reservation> reservOpt = reservationRepository
                .findOldestActiveReservation(isbn);

        if (reservOpt.isPresent())
        {
            Reservation r = reservOpt.get();

            if (Objects.equals(r.getBorrower(), userId))
            {
                r.setLending(lending.getId());
                reservationRepository.save(r);
            }
            else {
                throw new BookAlreadyReservedException(isbn);
            }
        }

        int count = lendingRepository.countActiveLendingsByUser(userId);

        if (count >= 3) {
            throw new MaxBooksExceededException(userId);
        }

        int numCopies = book.getCopies();

        if (numCopies < 1) {
            throw new BookNotAvailableException(isbn);
        }


        return lendingRepository.save(lending);
    }
}
