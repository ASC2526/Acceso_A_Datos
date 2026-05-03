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
import java.util.List;
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

        if (isbn == null || isbn.isBlank() || userId == null || userId.isBlank())
            throw new IllegalArgumentException("Book and borrower are required");

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFined() != null && user.getFined().isAfter(LocalDate.now())) {
            throw new UserFinedException(userId);
        }

        int count = lendingRepository.countActiveLendingsByUser(userId);

        if (count >= 3) {
            throw new MaxBooksExceededException(userId);
        }

        int activeLendings = lendingRepository.countActiveLendingsByBook(isbn);
        int available = book.getCopies() - activeLendings;

        if (available <= 0) {
            throw new BookNotAvailableException(isbn);
        }

        Optional<Reservation> reserveOpt = reservationRepository
                .findOldestActiveReservation(isbn);

        if (reserveOpt.isPresent())
        {
            Reservation r = reserveOpt.get();

            if (Objects.equals(r.getBorrower(), userId))
            {

                lending.setLendingdate(LocalDate.now());
                lending.setReturningdate(null);

                Lending saved = lendingRepository.save(lending);

                r.setLending(saved.getId());
                reservationRepository.save(r);

                return saved;
            }
            else {
                throw new BookAlreadyReservedException(isbn);
            }
        }
        lending.setLendingdate(LocalDate.now());
        lending.setReturningdate(null);
        return lendingRepository.save(lending);
    }

    public List<Lending> getAllLendings() {
        return lendingRepository.findAll();
    }


    public List<Lending> getLendingsByUser(String userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return lendingRepository.findByBorrower(userId);
    }

    public List<Lending> getActiveLendings() {
        return lendingRepository.findByReturningdateIsNull();
    }

    public List<Lending> getLendingsByBook(String isbn) {

        bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return lendingRepository.findByBook(isbn);
    }

    public Lending returnBook(String isbn, String userId)
    {
        bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Optional<Lending> activeLending = lendingRepository
                .findByBorrowerAndBook(userId, isbn);

        if(activeLending.isEmpty())
            throw new LendingNotActiveException(isbn, userId);

        Lending lending = activeLending.get();
        lending.setReturningdate(LocalDate.now());

        LocalDate lendingDate = lending.getLendingdate();
        LocalDate limitDate = lendingDate.plusDays(7);

        if (LocalDate.now().isAfter(limitDate)) {
            user.setFined(LocalDate.now().plusDays(15));
            userRepository.save(user);
        }

        Optional<Reservation> reserveOpt = reservationRepository
                .findOldestActiveReservation(isbn);

        if (reserveOpt.isEmpty())
            System.out.println("The book has been returned and there isn´t any active reservation for this book: " + isbn + ".");

        return lendingRepository.save(lending);
    }
}
