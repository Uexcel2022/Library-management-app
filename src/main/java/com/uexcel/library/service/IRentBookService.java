package com.uexcel.library.service;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.dto.UserBookDto;

public interface IRentBookService {
    /**
     * @param rentBookDto - will contain book rent info
     * @return will return status and message
     */
    LibraryResponseDto createBookRentDetails(RentBookDto rentBookDto);

    /**
     * @param userBookDto - contains booking information
     * @return return response status and body
     */
    LibraryResponseDto returnBook(UserBookDto userBookDto);

    LibraryResponseDto deleteRentBook(UserBookDto userBookDto, String resourceName);

    LibraryResponseDto fetchRentBook(String bookId,String phoneNumber, boolean returned);

    LibraryResponseDto deleteRentBook(String id);
}
