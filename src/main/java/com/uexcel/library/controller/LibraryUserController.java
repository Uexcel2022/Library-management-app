package com.uexcel.library.controller;

import com.uexcel.library.dto.DeleteUserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.LibraryUserDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryUserController {
    private final IUserService userService;
    @PostMapping("/create-user")
    public ResponseEntity<LibraryResponseDto> createUser(@Valid @RequestBody LibraryUserDto libraryUserDto){
        LibraryResponseDto lib = userService.createUser(libraryUserDto);
        return ResponseEntity.status(lib.getStatus()).body(lib);
    }

    @GetMapping("/fetch-user")
    public ResponseEntity<LibraryResponseDto> fetchUser(@RequestParam String emailOrPhoneNumber){
        LibraryResponseDto lib = userService.fetchUser(emailOrPhoneNumber);
        return ResponseEntity.status(lib.getStatus()).body(lib);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<LibraryResponseDto> deleteUser(@Valid @RequestBody DeleteUserBookDto deleteUserBookDto){
        LibraryResponseDto lib = userService.deleteUser(deleteUserBookDto);
        return ResponseEntity.status(lib.getStatus()).body(lib);
    }
}
