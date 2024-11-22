package com.uexcel.library.controller;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.service.IRentBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class RentBookController {
    private final IRentBookService rentBookService;
    @PostMapping("/rent")
    public ResponseEntity<LibraryResponseDto> rentBook(@RequestBody  RentBookDto rentBookDto) {
        LibraryResponseDto lb = rentBookService.createRentBook(rentBookDto);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }
}
