package com.uexcel.library.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.library.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "employee")
@Getter @Setter @ToString
@Schema(name = "LibraryAdmin", description = "The schema will hold employee details in addition to role.")
public class Admin extends UserDto {
    private String role;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;

}
