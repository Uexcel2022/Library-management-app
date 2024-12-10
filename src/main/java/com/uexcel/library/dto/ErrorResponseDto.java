package com.uexcel.library.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "ErrorResponse",description = "This schema will hold response status and message")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ErrorResponseDto {
    private String timestamp;
    private int status;
    private String description;
    private String message;
    private String apiPath;

}

