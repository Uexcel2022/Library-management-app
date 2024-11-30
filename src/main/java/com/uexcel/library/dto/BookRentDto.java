package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Schema(name = "BookRent",description = "This schema will hold details for rented books")
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
    private UserDto libraryUser;

}
