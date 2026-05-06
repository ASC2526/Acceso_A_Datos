package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.dto.ReturnResponseDTO;
import com.asc2526.da.unit5.library.exception.*;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.model.Reservation;
import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.LendingRepository;
import com.asc2526.da.unit5.library.repository.ReservationRepository;
import com.asc2526.da.unit5.library.repository.UserRepository;
import com.asc2526.da.unit5.library.util.LibraryConstants;
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

        String isbn = (lending.getBook() != null) ? lending.getBook().getIsbn() : null;
        String userId = (lending.getBorrower() != null) ? lending.getBorrower().getCode() : null;

        if (isbn == null || isbn.isBlank() || userId == null || userId.isBlank())
            throw new IllegalArgumentException("Book and borrower are required");

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        lending.setBook(book);
        lending.setBorrower(user);

        // 1 - verify fined
        if (user.getFined() != null && user.getFined().isAfter(LocalDate.now())) {
            throw new UserFinedException(userId);
        }

        // 2 - verify max lendings
        int count = lendingRepository.countActiveLendingsByUser(userId);
        if (count >= LibraryConstants.MAX_ACTIVE_LENDINGS) {
            throw new MaxBooksExceededException(userId);
        }

        // 3 - verify available copies
        int activeLendings = lendingRepository.countActiveLendingsByBook(isbn);
        int available = book.getCopies() - activeLendings;

        if (available <= 0) {
            throw new BookNotAvailableException(isbn);
        }

        // 4 - verify reservation
        Optional<Reservation> reserveOpt = reservationRepository
                .findOldestActiveReservation(isbn);

        if (reserveOpt.isPresent())
        {
            Reservation oldestReservation = reserveOpt.get();

            if (Objects.equals(oldestReservation.getBorrower().getCode(), userId))
            {

                lending.setLendingDate(LocalDate.now());
                lending.setReturningDate(null);

                Lending saved = lendingRepository.save(lending);

                oldestReservation.setLending(saved);
                reservationRepository.save(oldestReservation);

                return saved;
            }
            else {
                throw new BookAlreadyReservedException(isbn);
            }
        }
        lending.setLendingDate(LocalDate.now());
        lending.setReturningDate(null);
        return lendingRepository.save(lending);
    }

    public List<Lending> getAllLendings() {
        return lendingRepository.findAll();
    }


    public List<Lending> getLendingsByUser(String userId) {
        if (userId == null)
            throw new IllegalArgumentException("The user code cannot be null");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return lendingRepository.findByBorrower(user);
    }

    public List<Lending> getActiveLendings() {
        return lendingRepository.findByReturningDateIsNull();
    }

    public List<Lending> getLendingsByBook(String isbn) {
        if (isbn == null)
            throw new IllegalArgumentException("The book cannot be null");

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return lendingRepository.findByBook(book);
    }

    @Transactional
    public ReturnResponseDTO returnBook(String isbn, String userId)
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
        lending.setReturningDate(LocalDate.now());

        // fined logic
        LocalDate lendingDate = lending.getLendingDate();
        LocalDate limitDate = lendingDate.plusDays(LibraryConstants.LENDING_DAYS);

        String message = "Book returned successfully.";
        if (LocalDate.now().isAfter(limitDate)) {
            user.setFined(LocalDate.now().plusDays(LibraryConstants.FINE_DAYS));
            userRepository.save(user);
            message += " The user has been fined due to delay.";
        }

        // notify active reservations
        String nextReservationUser = null;
        Optional<Reservation> nextRes = reservationRepository
                .findOldestActiveReservation(isbn);

        if (nextRes.isPresent())
        {
            message = "ADVICE: the book returned has a pending reservation.";
            User resUser = userRepository.findById(nextRes.get().getBorrower().getCode()).orElse(null);
            if (resUser != null) {
                nextReservationUser = resUser.getName() + " " + resUser.getSurname() + " (" + resUser.getCode() + ")";
            }
        }
        Lending saved = lendingRepository.save(lending);
        return new ReturnResponseDTO(saved, message, nextReservationUser);
    }
}
