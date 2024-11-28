package com.uexcel.library.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class BookRentDto {
    private String id;
    private boolean paid;
    private boolean returned;
    @Positive(message = "Quantity must be greater than zero.")
    private int quantity;
    private LocalDate date;
    private LocalDate dueDate;
    @Positive(message = "Amount must be greater than zero.")
    private double amount;
    private BookDto book;
    private LibraryUserDto libraryUser;

}
