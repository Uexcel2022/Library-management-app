package com.uexcel.library.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter

public class BookRequestDto {
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Title is a required field.")
    private String title;
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
    @Pattern(regexp = "[A-Za-z0-9 -]+",message = "isbn is a required field.")
    private String isbn;
    @Pattern(regexp = "[A-Za-z]+",message = "Language is a required field.")
    private String language;
    @Pattern(regexp = "[1-9]+[a-z ]+edition",message = "Language is a required field.")
    private String edition;
    @Past(message = "Publish date should be less than today.")
    private LocalDate publishedDate;
    @PositiveOrZero(message = "Quantity must be positive or zero.")
    private int quantity;
    @Positive(message = "Price must be greater than zero.")
    private double price;
    @Pattern(regexp = "[A-Za-z]+",message = "Genre should contain only alphabets.")
    private String genre;
}