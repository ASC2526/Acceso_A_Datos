package com.asc2526.da.unit5.model;

import java.time.LocalDate;

public class Lending {

    private Integer id;
    private LocalDate lendingDate;
    private LocalDate returningDate;
    private Book book;
    private User borrower;

    public Lending() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getLendingDate() { return lendingDate; }
    public void setLendingDate(LocalDate lendingDate) { this.lendingDate = lendingDate; }

    public LocalDate getReturningDate() { return returningDate; }
    public void setReturningDate(LocalDate returningDate) { this.returningDate = returningDate; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getBorrower() { return borrower; }
    public void setBorrower(User borrower) { this.borrower = borrower; }
}