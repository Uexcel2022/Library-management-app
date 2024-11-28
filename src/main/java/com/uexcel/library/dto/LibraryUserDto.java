package com.uexcel.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LibraryUserDto {
    private String id;
    @Pattern(regexp = "[A-Za-z]+",message = "First name should contain only alphabet characters.")
    private String firstName;
    @Pattern(regexp = "[A-Za-z]+",message = "Last name should contain only alphabet characters.")
    private String lastName;
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Please enter a valid Nigeria phone number.")
    private String phoneNumber;

    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Email address is required.")
    @NotNull(message = "Email address is required.")
    private String email;
}
