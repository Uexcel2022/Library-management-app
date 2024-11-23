package com.uexcel.library.controller;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/fetch-book")
    public ResponseEntity<LibraryResponseDto> fetchBook(@RequestParam String bootTitle,
                                                        @RequestParam String author) {
        LibraryResponseDto lb = bookService.fetchBook(bootTitle,author);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
}
