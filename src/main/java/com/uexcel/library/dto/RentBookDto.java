package com.uexcel.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RentBookDto {
    private boolean paid;
    private boolean returned;
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Invalid mobile number.")
    private String phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Title is a required field")
    private String bookTile;
    @Pattern(regexp = "[A-Za-z ]+",message = "Author is a required field")
    private String author;
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private int quantity;
    private LocalDate date;
    private LocalDate dueDate;
    @Positive(message = "Amount must be greater than zero")
    private double amount;
}
