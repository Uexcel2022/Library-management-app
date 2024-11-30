package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Schema(name="PasswordChange",description = "The scheme will hold password to be updated.")
@Getter @Setter
public class PasswordChangeDto {
    @NotNull(message = "Old password is required")
    @NotEmpty(message = "Old password is required")
    private String oldPassword;
    @NotNull(message = "New Password is required")
    @NotEmpty(message = "New Password is required")
    private String newPassword;
    @NotNull(message = "Please confirm new password")
    @NotEmpty(message = "Please confirm new password")
    private String confirmNewPassword;
}
