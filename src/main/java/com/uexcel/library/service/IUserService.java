package com.uexcel.library.service;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.LibraryUserDto;

public interface IUserService {
    /**
     * @param libraryUserDto  - contains user information
     * @return status and message
     */
    LibraryResponseDto createUser(LibraryUserDto libraryUserDto);


    LibraryResponseDto fetchUser(String emailOrPhoneNumber);
}
