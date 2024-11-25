package com.uexcel.library.exception;

import com.uexcel.library.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.uexcel.library.service.IBookService.getTime;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        errors.forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            body.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);


    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>
    handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
      return   ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(getTime(),404,"Not Found",
                        e.getMessage(),webRequest.getDescription(false)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto>
    handleRunningRentException(BadRequestException e, WebRequest webRequest) {
        return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(getTime(),400,"Bad Request",
                        e.getMessage(),webRequest.getDescription(false)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>
    handleExceptions(Exception e, WebRequest webRequest) {
        return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDto(getTime(),500,"Internal Server Error",
                        e.getMessage(),webRequest.getDescription(false)));
    }

}
