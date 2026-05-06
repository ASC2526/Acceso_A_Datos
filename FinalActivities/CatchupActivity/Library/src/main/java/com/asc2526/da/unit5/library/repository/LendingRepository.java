package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.model.User;
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
       AND l.returningDate IS NULL
       """)
    int countActiveLendingsByUser(@Param("borrower") String borrower);

    @Query("""
    SELECT COUNT(l)
    FROM Lending l
    WHERE l.book = :isbn
    AND l.returningDate IS NULL
""")
    int countActiveLendingsByBook(@Param("isbn") String isbn);

    List<Lending> findByBorrower(User borrower);

    List<Lending> findByReturningDateIsNull();

    List<Lending> findByBook(Book book);

    @Query("""
    SELECT l
    FROM Lending l
    WHERE l.book = :isbn
    AND l.borrower = :borrower
    AND l.returningDate IS NULL
""")
    Optional<Lending> findByBorrowerAndBook(@Param("borrower") String userId, @Param("isbn") String isbn);

}
