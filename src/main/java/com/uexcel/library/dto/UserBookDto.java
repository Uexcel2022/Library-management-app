package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Schema(name = "UserBook",description = "This schema will hold information about user and " +
        "book in relation to book renting management.")
@Getter @Setter
public class UserBookDto {

    @NotNull(message = "Phone number is required.")
    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Invalid mobile number.")
    private String phoneNumber;

    @NotNull(message = "Tile is required.")
    @NotEmpty(message = "Title is required.")
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Book title should not contain special characters.")
    private String title;

    @NotNull(message = "Author is required.")
    @NotEmpty(message = "Author is required.")
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
}
