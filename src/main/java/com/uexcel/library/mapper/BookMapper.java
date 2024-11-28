package com.uexcel.library.mapper;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.BookRequestDto;

public class BookMapper{
    public static Book mapToNewBook(BookRequestDto bookRequestDto, Book book){
        book.setAuthor(bookRequestDto.getAuthor());
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setEdition(bookRequestDto.getEdition());
        book.setPublishedDate(bookRequestDto.getPublishedDate());
        book.setLanguage(bookRequestDto.getLanguage());
        book.setPrice(bookRequestDto.getPrice());
        book.setQuantity(bookRequestDto.getQuantity());
        book.setBorrowed(0);
        book.setAvailable(bookRequestDto.getQuantity());
        return book;

    }

    public static Book mapToUpdateBook(BookDto bookDto, Book book){
        book.setId(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setEdition(bookDto.getEdition());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setLanguage(bookDto.getLanguage());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(book.getQuantity()+bookDto.getQuantity());
        book.setAvailable(book.getQuantity()+bookDto.getQuantity());
        book.setBorrowed(book.getBorrowed()+bookDto.getBorrowed());
        book.setGenre(bookDto.getGenre());
        return book;
    }


    public static BookDto mapToBookDto(Book book, BookDto bookDto){
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setEdition(book.getEdition());
        bookDto.setPublishedDate(book.getPublishedDate());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setPrice(book.getPrice());
        bookDto.setQuantity(book.getQuantity());
        bookDto.setBorrowed(book.getBorrowed());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setGenre(book.getGenre());
        return bookDto;
    }
}
