package com.uexcel.library.service;

import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.LibraryUserDto;

public interface IUserService {
    /**
     * @param libraryUserDto  - contains user information
     * @return status and message
     */
    LibraryResponseDto createUser(LibraryUserDto libraryUserDto);


    LibraryResponseDto fetchUser(String emailOrPhoneNumber);

    /**
     * @param userBookDto - will hold information for deleting user
     * @return - response status and message
     */
    LibraryResponseDto deleteUser(UserBookDto userBookDto);

}
