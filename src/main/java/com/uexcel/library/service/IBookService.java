package com.uexcel.library.service;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.*;
import com.uexcel.library.exception.ResourceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface IBookService {
    /**
     * @param bookRequestDto - will hold book properties
     * @return - will hold response information
     */
    ResponseDto createBook(BookRequestDto bookRequestDto);

    /**
     * @param bookTitle - book tile
     * @param author    - book author
     * @return will hold response information
     */
    BookDto fetchBook(String bookTitle, String author);

    ResponseDto updateBook(BookDto bookDto);

    List<BookDto> fetchAllBooks(String author, String genre);

    ResponseDto deleteBook(String title, String author, String bookId);

    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    static void validateBookNotNull(Book book, String title, String author){
        if(book == null) {
            throw new ResourceNotFoundException(
                    String.format("Book with title: %s and author: %s not found.", title,author));
        }

    }
}
