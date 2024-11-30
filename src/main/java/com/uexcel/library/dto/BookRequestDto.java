package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Schema(name = "BookRequest",description = "This schema will hold information for " +
        "creating and updating a book.")
@Getter @Setter
public class BookRequestDto {
    @NotNull(message = "Title is required.")
    @NotEmpty(message = "Tile is required.")
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Title is a required field.")
    private String title;
    @NotNull(message = "Author is required.")
    @NotEmpty(message = "Author is required.")
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
    @Pattern(regexp = "[A-Za-z0-9 -]+",message = "isbn is a required field.")
    @NotNull(message = "ISBN is required.")
    @NotEmpty(message = "ISBN is required.")
    private String isbn;
    @NotNull(message = "Language is required.")
    @NotEmpty(message = "Language is required.")
    @Pattern(regexp = "[A-Za-z]+",message = "Language is a required field.")
    private String language;
    private String edition;
    @Past(message = "Publish date should be less than today.")
    private LocalDate publishedDate;
    @PositiveOrZero(message = "Quantity must be positive or zero.")
    private int quantity;
    @Positive(message = "Price must be greater than zero.")
    private double price;
    @NotNull(message = "Genre is required.")
    @NotEmpty(message = "Genre is required.")
    @Pattern(regexp = "[A-Za-z]+",message = "Genre should contain only alphabets.")
    private String genre;
}
