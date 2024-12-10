package com.uexcel.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Response",description = "This schema will hold response status and message")
@Getter @Setter
public class ResponseDto {
    private int status;
    private String description;
    private String message;
}
