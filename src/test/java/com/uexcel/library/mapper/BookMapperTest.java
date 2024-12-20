package com.uexcel.library.mapper;

import com.uexcel.library.model.Book;
import com.uexcel.library.model.Genre;
import com.uexcel.library.dto.BookRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    BookRequestDto bookRequestDto;
    Genre genre;
    @BeforeEach
    void setUp() {
        bookRequestDto = new BookRequestDto();
        genre = new Genre();
    }

    @Test
    public void BookMapperTest() {
        bookRequestDto.setAuthor("Ai");
        bookRequestDto.setGenre("thriller");
        bookRequestDto.setIsbn("123456789");
        bookRequestDto.setTitle("Java");
        bookRequestDto.setIsbn("2021-01-4");
        bookRequestDto.setLanguage("english");
        bookRequestDto.setPrice(30.95);
        bookRequestDto.setQuantity(100);


       Book book = BookMapper.mapToNewBook(bookRequestDto,new Book());

        assertEquals(bookRequestDto.getAuthor(), book.getAuthor());
        assertEquals(bookRequestDto.getTitle(), book.getTitle());
        assertEquals(bookRequestDto.getIsbn(), book.getIsbn());
        assertEquals(bookRequestDto.getLanguage(), book.getLanguage());
        assertEquals(bookRequestDto.getPrice(), book.getPrice());
        assertEquals(bookRequestDto.getQuantity(), book.getQuantity());
        assertEquals(bookRequestDto.getPublishedDate(), book.getPublishedDate());

    }
}