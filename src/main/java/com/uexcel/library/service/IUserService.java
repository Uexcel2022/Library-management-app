package com.uexcel.library.service;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.LibraryUserDto;
import com.uexcel.library.dto.RentBookDto;

public interface IUserService {
    /**
     * @param libraryUserDto  - contains user information
     * @return status and message
     */
    LibraryResponseDto createUser(LibraryUserDto libraryUserDto);


    LibraryResponseDto fetchUser(String emailOrPhoneNumber);

    /**
     * @param rentBookDto - will hold information for deleting user
     * @return - response status and message
     */
    LibraryResponseDto deleteUser(RentBookDto rentBookDto);

}
