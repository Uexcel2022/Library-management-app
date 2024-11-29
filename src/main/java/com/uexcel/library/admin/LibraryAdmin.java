package com.uexcel.library.admin;

import com.uexcel.library.dto.EmployeeDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "employee")
@Getter @Setter @ToString
public class LibraryAdmin extends EmployeeDto {
    @NotEmpty(message = "Role is required.")
    @NotNull(message = "Role is required")
    private String role;
}
