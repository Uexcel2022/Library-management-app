package com.uexcel.library.controller;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.DeleteUserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.service.IBookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@AllArgsConstructor
@Validated
public class BookController {
    private final IBookService bookService;
    @PostMapping("/create-book")
    public ResponseEntity<LibraryResponseDto> createBooks(@Valid @RequestBody BookDto bookDto) {
       LibraryResponseDto lb = bookService.createBook(bookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
    @PostMapping("/update-book")
    public ResponseEntity<LibraryResponseDto> fetchBook(@Valid@ RequestBody BookDto bookDto) {
        LibraryResponseDto lb = new LibraryResponseDto();
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
    @DeleteMapping("/delete-book")
    public ResponseEntity<LibraryResponseDto> deleteBook(@Valid @RequestBody DeleteUserBookDto deleteUserBookDto) {
        LibraryResponseDto lb = bookService.deleteBook(deleteUserBookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @GetMapping("/fetch-all-books")
    public ResponseEntity<LibraryResponseDto> fetchAllBooks(
            @RequestParam(required = false) String author, @RequestParam(required = false) String genre) {
        LibraryResponseDto lb = bookService.fetchAllBooks(author,genre);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
}
