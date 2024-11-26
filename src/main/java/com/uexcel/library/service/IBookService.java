package com.uexcel.library.service;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBookService {
    /**
     * @param bookDto - will hold book properties
     * @return - will hold response information
     */
 LibraryResponseDto createBook(BookDto bookDto);

    /**
     * @param bookTitle  - book tile
     * @param author - book author
     * @return will hold response information
     */
 LibraryResponseDto fetchBook(String bookTitle, String author);

 LibraryResponseDto updateBook(BookDto bookDto);

LibraryResponseDto fetchAllBooks(String author, String genre);

 LibraryResponseDto deleteBook(UserBookDto userBookDto);

    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
