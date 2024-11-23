package com.uexcel.library.exception;

import com.uexcel.library.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpResponse;

import static com.uexcel.library.service.IBookService.getTime;

@RestControllerAdvice
public class GlobalException {
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
    handleException(Exception e, WebRequest webRequest) {
        return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDto(getTime(),500,"Internal Server Error",
                        e.getMessage(),webRequest.getDescription(false)));
    }

}
