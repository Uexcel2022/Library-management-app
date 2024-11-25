package com.uexcel.library.service;

import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;

public interface IRentBookService {
    /**
     * @param rentBookDto - will contain book rent info
     * @return will return status and message
     */
    LibraryResponseDto createRentBook(RentBookDto rentBookDto);

    LibraryResponseDto returnBook(RentBookDto rentBookDto);

    LibraryResponseDto deleteRentBook(RentBookDto rentBookDto, String resourceName);

//    LibraryResponseDto deleteRentBook(RentBookDto rentBookDto);

    LibraryResponseDto deleteRentBook(String id);
}
