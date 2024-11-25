package com.uexcel.library.controller;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.service.IRentBookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class RentBookController {
    private final IRentBookService rentBookService;
    @PostMapping("/rent")
    public ResponseEntity<LibraryResponseDto> rentBook(@Valid @RequestBody  RentBookDto rentBookDto) {
        LibraryResponseDto lb = rentBookService.createRentBook(rentBookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
    @PutMapping("/return")
    public ResponseEntity<LibraryResponseDto> returnBook(@Valid @RequestBody  RentBookDto rentBookDto) {
        LibraryResponseDto lb = rentBookService.returnBook(rentBookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @DeleteMapping("/delete-rent")
    public ResponseEntity<LibraryResponseDto> deleteRent(@Valid@RequestBody  RentBookDto rentBookDto) {
        LibraryResponseDto lb = rentBookService.deleteRentBook(rentBookDto,"Book rent details");
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @DeleteMapping("/delete-rent-id")
    public ResponseEntity<LibraryResponseDto> deleteRentById(@RequestParam(required = false) String rentId) {
        LibraryResponseDto lb = rentBookService.deleteRentBook(rentId);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @GetMapping("/fetch-rent")
    public ResponseEntity<LibraryResponseDto> fetchRent(@RequestParam(required = false) String bookId,
                                                        @RequestParam(required = false) String userId) {
//        LibraryResponseDto lb = rentBookService.deleteRentBook(rentBookDto);
//        return ResponseEntity.status(lb.getStatus()).body(lb);
        return null;
    }



}
