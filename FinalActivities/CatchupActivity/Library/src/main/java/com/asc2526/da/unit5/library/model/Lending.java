package com.asc2526.da.unit5.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lendings")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lendingdate", nullable = false)
    private LocalDate lendingdate;

    @Column(name = "returningdate")
    private LocalDate returningdate;

    @Column(name = "book", nullable = false)
    private String book; // ISBN

    @Column(name = "borrower", nullable = false)
    private String borrower; // user code

    public Lending() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getLendingdate() { return lendingdate; }
    public void setLendingdate(LocalDate lendingdate) { this.lendingdate = lendingdate; }

    public LocalDate getReturningdate() { return returningdate; }
    public void setReturningdate(LocalDate returningdate) { this.returningdate = returningdate; }

    public String getBook() { return book; }
    public void setBook(String book) { this.book = book; }

    public String getBorrower() { return borrower; }
    public void setBorrower(String borrower) { this.borrower = borrower; }
}