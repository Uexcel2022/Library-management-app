package com.uexcel.library.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EmployeeDto extends LibraryUserDto{
    @NotNull(message = "Password is required.")
    @NotEmpty(message = "Password is required.")
    private String password;
}
