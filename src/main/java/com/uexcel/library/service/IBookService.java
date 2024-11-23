package com.uexcel.library.service;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.LibraryResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBookService {
    /**
     * @param bookDto - will hold book properties
     * @return - will hold response information
     */
 LibraryResponseDto createBook(BookDto bookDto);

 LibraryResponseDto fetchBook(String bookTitle,String author);

    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
