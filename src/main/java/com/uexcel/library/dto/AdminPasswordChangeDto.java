package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(name="AdminPasswordChange",description = "The schema will hold new password info.")
@Getter @Setter
public class AdminPasswordChangeDto {
    @NotNull(message = "email password is required")
    @NotEmpty(message = "email password is required")
    private String email;
    @NotNull(message = "New Password is required")
    @NotEmpty(message = "New Password is required")
    private String newPassword;
    @NotNull(message = "Please confirm new password")
    @NotEmpty(message = "Please confirm password")
    private String confirmPassword;
}
