package com.uexcel.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String description;
    private String message;
    private String apiPath;

}

