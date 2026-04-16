package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LendingRepository extends JpaRepository<Lending, Integer> {
    @Query("""
       SELECT COUNT(l)
       FROM Lending l
       WHERE l.borrower = :borrower
       AND l.returningdate IS NULL
       """)
    int countActiveLendingsByUser(@Param("borrower") String borrower);
}
