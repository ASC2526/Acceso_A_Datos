package com.asc2526.da.unit5.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "book", nullable = false)
    private String book;

    @Column(name = "borrower", nullable = false)
    private String borrower;

    @Column(name = "lending")
    private Integer lending;

    public Reservation() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getBook() { return book; }
    public void setBook(String book) { this.book = book; }

    public String getBorrower() { return borrower; }
    public void setBorrower(String borrower) { this.borrower = borrower; }

    public Integer getLending() { return lending; }
    public void setLending(Integer lending) { this.lending = lending; }
}