package com.uexcel.library.mapper;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.Genre;
import com.uexcel.library.dto.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    BookDto bookDto;
    Genre genre;
    @BeforeEach
    void setUp() {
        bookDto = new BookDto();
        genre = new Genre();
    }

    @Test
    public void BookMapperTest() {
        bookDto.setAuthor("Ai");
        bookDto.setGenre("thriller");
        bookDto.setIsbn("123456789");
        bookDto.setTitle("Java");
        bookDto.setIsbn("2021-01-4");
        bookDto.setLanguage("english");
        bookDto.setPrice(30.95);
        bookDto.setQuantity(100);
        bookDto.setBorrowed(10);
        bookDto.setAvailable(90);

       Book book = BookMapper.mapToBook(bookDto,new Book());

        assertEquals(bookDto.getAuthor(), book.getAuthor());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getIsbn(), book.getIsbn());
        assertEquals(bookDto.getLanguage(), book.getLanguage());
        assertEquals(bookDto.getPrice(), book.getPrice());
        assertEquals(bookDto.getQuantity(), book.getQuantity());
        assertEquals(bookDto.getBorrowed(), book.getBorrowed());
        assertEquals(bookDto.getAvailable(), book.getAvailable());
        assertEquals(bookDto.getPublishedDate(), book.getPublishedDate());

    }
}