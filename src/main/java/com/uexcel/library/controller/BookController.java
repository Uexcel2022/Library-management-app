package com.uexcel.library.controller;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class BookController {
    private final IBookService bookService;
    @PostMapping("/create-book")
    public ResponseEntity<LibraryResponseDto> createBooks(@RequestBody BookDto bookDto) {
       LibraryResponseDto lb = bookService.createBook(bookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
}
