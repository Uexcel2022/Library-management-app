package com.uexcel.library.service.impl;

import com.uexcel.library.model.Book;
import com.uexcel.library.model.Genre;
import com.uexcel.library.dto.BookRequestDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.service.IGenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IBookServiceImplTest {
    @InjectMocks
    private IBookServiceImpl bookServiceImpl;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private IGenreService iGenreService;


    Book book;
    Genre genre;
    BookRequestDto bookRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        genre = new Genre();
        bookRequestDto = new BookRequestDto();

    }

    @Test
    public void createBookTest(){
        bookRequestDto.setAuthor("Ai");
        bookRequestDto.setGenre("thriller");
        bookRequestDto.setIsbn("123456789");
        bookRequestDto.setTitle("Java");
        bookRequestDto.setIsbn("2021-01-4");
        bookRequestDto.setGenre("thriller");
        bookRequestDto.setEdition("13th edition");
        bookRequestDto.setPrice(30.95);
        bookRequestDto.setQuantity(100);

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());

        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setLanguage(bookRequestDto.getLanguage());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublishedDate(bookRequestDto.getPublishedDate());
        book.setEdition(bookRequestDto.getEdition());
        book.setPrice(bookRequestDto.getPrice());
        book.setQuantity(bookRequestDto.getQuantity());

        when(iGenreService.fetchGenreByName(genre.getGenreName())).thenReturn(genre);
        bookRepository.save(book);

        ResponseDto rs = bookServiceImpl.createBook(bookRequestDto);

        assertEquals(201, rs.getStatus());
        assertEquals("Created", rs.getDescription());
        assertEquals("Book created successfully.", rs.getMessage());
        verify(bookRepository,times(1)).save(book);
        verify(iGenreService,times(1)).fetchGenreByName(bookRequestDto.getGenre());

    }

    @Test
    public void createBookAndBookExistTest(){
        bookRequestDto.setAuthor("Ai");
        bookRequestDto.setGenre("thriller");
        bookRequestDto.setIsbn("123456789");
        bookRequestDto.setTitle("Java");
        bookRequestDto.setIsbn("2021-01-4");
        bookRequestDto.setGenre("thriller");
        bookRequestDto.setEdition("13th edition");
        bookRequestDto.setPrice(30.95);
        bookRequestDto.setQuantity(100);

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());
        book.setGenre(genre);

        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setLanguage(bookRequestDto.getLanguage());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublishedDate(bookRequestDto.getPublishedDate());
        book.setEdition(bookRequestDto.getEdition());
        book.setQuantity(bookRequestDto.getQuantity());

        when(bookRepository.findByTitleAndAuthor(bookRequestDto.getTitle(), bookRequestDto.getAuthor())).thenReturn(book);

        assertThrows(BadRequestException.class, () -> bookServiceImpl.createBook(bookRequestDto));


    }
}