package com.uexcel.library.exception;

import com.uexcel.library.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.uexcel.library.service.IBookService.getTime;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>
    handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
      return   ResponseEntity.status(404).body(
                new ErrorResponseDto(getTime(),404,"Not Found",
                        e.getMessage(),webRequest.getDescription(false)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto>
    handleRunningRentException(BadRequestException e, WebRequest webRequest) {
        return   ResponseEntity.status(400).body(
                new ErrorResponseDto(getTime(),400,"Bad Request",
                        e.getMessage(),webRequest.getDescription(false)));
    }

}
