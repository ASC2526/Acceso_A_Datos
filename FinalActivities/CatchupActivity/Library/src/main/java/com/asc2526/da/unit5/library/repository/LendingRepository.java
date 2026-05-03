package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LendingRepository extends JpaRepository<Lending, Integer> {
    @Query("""
       SELECT COUNT(l)
       FROM Lending l
       WHERE l.borrower = :borrower
       AND l.returningdate IS NULL
       """)
    int countActiveLendingsByUser(@Param("borrower") String borrower);

    @Query("""
    SELECT COUNT(l)
    FROM Lending l
    WHERE l.book = :isbn
    AND l.returningdate IS NULL
""")
    int countActiveLendingsByBook(@Param("isbn") String isbn);

    List<Lending> findByBorrower(String borrower);

    List<Lending> findByReturningdateIsNull();

    List<Lending> findByBook(String book);

    @Query("""
    SELECT l
    FROM Lending l
    WHERE l.book = :isbn
    AND l.borrower = :borrower
    AND l.returningdate IS NULL
""")
    Optional<Lending> findByBorrowerAndBook(@Param("borrower") String userId, @Param("isbn") String isbn);

}
