package com.asc2526.da.unit5.model;

public class Lending {

    private Integer id;
    private String lendingDate;
    private String returningDate;
    private Book book;
    private User borrower;

    public Lending() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLendingDate() { return lendingDate; }
    public void setLendingDate(String lendingDate) { this.lendingDate = lendingDate; }

    public String getReturningDate() { return returningDate; }
    public void setReturningDate(String returningDate) { this.returningDate = returningDate; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getBorrower() { return borrower; }
    public void setBorrower(User borrower) { this.borrower = borrower; }
}