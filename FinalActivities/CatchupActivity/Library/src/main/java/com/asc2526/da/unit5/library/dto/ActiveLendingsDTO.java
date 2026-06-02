package com.asc2526.da.unit5.library.dto;

import java.time.LocalDate;

public class ActiveLendingsDTO {
    private String isbn;
    private String title;
    private String borrowedBy;
    private LocalDate dueTo;
    private String delayed;

    public ActiveLendingsDTO(String isbn, String title, String borrowedBy, LocalDate dueTo, String delayed)
    {
        this.isbn = isbn;
        this.title = title;
        this.borrowedBy = borrowedBy;
        this.dueTo = dueTo;
        this.delayed = delayed;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBorrowedBy() { return borrowedBy; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }

    public LocalDate getDueTo() { return dueTo; }
    public void setDueTo(LocalDate dueTo) { this.dueTo = dueTo; }

    public String getDelayed() {
        return delayed;
    }
    public void setDelayed(String delayed) {
        this.delayed = delayed;
    }

}
