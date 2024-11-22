package com.uexcel.library.controller;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    public LibraryResponseDto createUser(@RequestBody UserDto userDto){
    }
}
