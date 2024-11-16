package com.uexcel.library.mapper;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.BookDto;

public class BookMapper{
    public static Book mapToBook(BookDto bookDto, Book book){
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setEdition(bookDto.getEdition());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setLanguage(bookDto.getLanguage());
        return book;
    }
}
