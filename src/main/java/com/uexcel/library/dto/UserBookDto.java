package com.uexcel.library.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserBookDto {
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Invalid mobile number.")
    private String phoneNumber;
    @Pattern(regexp = "[A-Za-z0-9 ]+",message = "Book title should not contain special characters.")
    private String title;
    @Pattern(regexp = "[A-Za-z ]+",message = "Author name should contain only alphabets.")
    private String author;
}
