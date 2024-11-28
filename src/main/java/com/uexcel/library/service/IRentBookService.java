package com.uexcel.library.service;

import com.uexcel.library.dto.*;

import java.util.List;

public interface IRentBookService {
    /**
     * @param bookRentRequestDto - will contain book rent info
     * @return will return status and message
     */
    BookRentDto createBookRentDetails(BookRentRequestDto bookRentRequestDto);

    /**
     * @param userBookDto - contains booking information
     * @return return response status and body
     */
    ResponseDto returnBook(UserBookDto userBookDto);

    ResponseDto deleteRentBook(UserBookDto userBookDto, String resourceName);

    List<BookRentDto> fetchRentBook(String bookId, String phoneNumber, boolean returned, String rentId);

}
