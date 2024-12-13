package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "LibraryUser",description = "This schema will hold information about the user.")
@Getter @Setter @ToString
public class UserDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;
    @NotNull(message = "first name is required.")
    @NotEmpty(message = "first name is required.")
    @Pattern(regexp = "[A-Za-z]+",message = "First name should contain only alphabet characters.")
    private String firstName;
    @NotNull(message = "Last name is required.")
    @NotEmpty(message = "Last name is required.")
    @Pattern(regexp = "[A-Za-z]+",message = "Last name should contain only alphabet characters.")
    private String lastName;
    @NotNull(message = "Phone number is required")
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "0[7-9][01][0-9]{8}",message = "Please enter a valid Nigeria phone number.")
    private String phoneNumber;
    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Email address is required.")
    @NotNull(message = "Email address is required.")
    private String email;
}
