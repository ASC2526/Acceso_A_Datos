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
    private LocalDate lendingDate;

    @Column(name = "returningdate")
    private LocalDate returningDate;

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code")
    private User borrower;

    public Lending() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getLendingDate() { return lendingDate; }
    public void setLendingDate(LocalDate lendingdate) { this.lendingDate = lendingdate; }

    public LocalDate getReturningDate() { return returningDate; }
    public void setReturningDate(LocalDate returningdate) { this.returningDate = returningdate; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getBorrower() { return borrower; }
    public void setBorrower(User borrower) { this.borrower = borrower; }
}