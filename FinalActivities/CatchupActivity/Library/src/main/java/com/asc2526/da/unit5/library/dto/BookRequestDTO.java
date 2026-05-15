package com.asc2526.da.unit5.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookRequestDTO {
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97[89])?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    private String isbn;

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 0, message = "Copies cannot be negative")
    private int copies;

    @NotBlank(message = "Category code is required")
    private String categoryCode;

    public BookRequestDTO() {}

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
}