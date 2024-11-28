package com.uexcel.library.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "Responses",description = "This schema will hold response status and message")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;
    private int status;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String apiPath;

}

