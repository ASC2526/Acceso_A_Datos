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

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code")
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "lending")
    private Lending lending;

    public Reservation() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getBorrower() { return borrower; }
    public void setBorrower(User borrower) { this.borrower = borrower; }

    public Lending getLending() { return lending; }
    public void setLending(Lending lending) { this.lending = lending; }
}