package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Schema(name = "Employee", description = "The schema will hold employee details")
@Getter @Setter @ToString
public class EmployeeDto extends UserDto {
    @NotEmpty(message = "Password is required.")
    @NotNull(message = "Password is required")
    private String password;
}
