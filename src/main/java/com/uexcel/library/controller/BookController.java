package com.uexcel.library.controller;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BookController {
    public ResponseEntity<ResponseDto> createBooks(@RequestBody BookDto bookDto) {
        return null;
    }
}
