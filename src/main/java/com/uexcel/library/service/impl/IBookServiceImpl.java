package com.uexcel.library.service.impl;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IBookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    /**
     * @param bookDto - will hold book properties
     * @return - will hold response information
     */
    @Override
    public ResponseDto createBook(BookDto bookDto) {
        return null;
    }
}
