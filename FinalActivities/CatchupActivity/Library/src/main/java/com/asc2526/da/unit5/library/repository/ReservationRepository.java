package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("""
       SELECT r FROM Reservation r
       WHERE r.book = :book
       AND r.lending IS NULL
       ORDER BY r.date ASC
       """)
    Optional<Reservation> findOldestActiveReservation(@Param("book") String book);

    List<Reservation> findByBook(String book);

    List<Reservation> findByBorrower(String borrower);

    @Query("""
       SELECT r FROM Reservation r
       WHERE r.book = :book
       AND r.borrower = :borrower
       AND r.lending IS NULL
       """)
    Optional<Reservation> findReservationByBookAndBorrowerAndLendingNull(@Param("book") String book, @Param("borrower") String borrower);
}