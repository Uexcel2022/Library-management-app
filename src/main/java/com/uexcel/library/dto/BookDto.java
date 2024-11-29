package com.uexcel.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.library.Entity.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Schema(name = "Book",description = "This schema will hold book details.")
@Getter @Setter @ToString
public class BookDto{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Title is a required field.")
    private String title;
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
    @Pattern(regexp = "[A-Za-z0-9 -]+",message = "isbn is a required field.")
    private String isbn;
    @Pattern(regexp = "[A-Za-z]+",message = "Language is a required field.")
    private String language;
    private String edition;
    @Past(message = "Publish date should be less than today.")
    private LocalDate publishedDate;
    @Positive(message = "Price must be greater than zero.")
    private double price;
    @PositiveOrZero(message = "Quantity must be positive or zero.")
    private int quantity;
    @PositiveOrZero(message = "Borrow copy must be positive or zero.")
    private int borrowed;
    @PositiveOrZero(message = "Available books must be positive or zero.")
    private int available;
    private Genre genre;

}
