package com.uexcel.library.service;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.ResponseDto;

public interface IBookService {
    /**
     * @param bookDto  - will hold book properties
     * @return - will hold response information
     */
 ResponseDto createBook(BookDto bookDto);
}
