package com.uexcel.library.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookRentRequestDto {
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Title is a required field.")
    private String title;
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
    @Positive(message = "Quantity must be greater than zero.")
    private int quantity;
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Please enter a valid Nigeria phone number.")
    private String phoneNumber;
}
