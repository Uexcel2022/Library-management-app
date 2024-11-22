package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.Genre;
import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.LibraryResponseDto;
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
    BookDto bookDto;
    LibraryResponseDto lb;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        genre = new Genre();
        bookDto = new BookDto();
        lb = new LibraryResponseDto();
    }

    @Test
    public void createBookTest(){
        bookDto.setAuthor("Ai");
        bookDto.setGenre("thriller");
        bookDto.setIsbn("123456789");
        bookDto.setTitle("Java");
        bookDto.setIsbn("2021-01-4");
        bookDto.setGenre("thriller");
        bookDto.setEdition("13th edition");
        bookDto.setPrice(30.95);
        bookDto.setQuantity(100);
        bookDto.setBorrowed(10);
        bookDto.setAvailable(90);

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());

        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setGenre(genre);

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setLanguage(bookDto.getLanguage());
        book.setIsbn(bookDto.getIsbn());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setEdition(bookDto.getEdition());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(bookDto.getQuantity());
        book.setBorrowed(bookDto.getBorrowed());
        book.setGenre(lb.getGenre());

        when(iGenreService.fetchGenreByName(genre.getGenreName())).thenReturn(lb);
        bookRepository.save(book);

        LibraryResponseDto bk = bookServiceImpl.createBook(bookDto);

        assertEquals(201, bk.getStatus());
        assertEquals("Created", bk.getDescription());
        assertEquals("Book created successfully.", bk.getMessage());
        verify(bookRepository,times(1)).save(book);
        verify(iGenreService,times(1)).fetchGenreByName(bookDto.getGenre());

    }

    @Test
    public void createBookAndBookExistTest(){
        bookDto.setAuthor("Ai");
        bookDto.setGenre("thriller");
        bookDto.setIsbn("123456789");
        bookDto.setTitle("Java");
        bookDto.setIsbn("2021-01-4");
        bookDto.setGenre("thriller");
        bookDto.setEdition("13th edition");
        bookDto.setPrice(30.95);
        bookDto.setQuantity(100);
        bookDto.setBorrowed(10);
        bookDto.setAvailable(90);

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());
        book.setGenre(genre);

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setLanguage(bookDto.getLanguage());
        book.setIsbn(bookDto.getIsbn());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setEdition(bookDto.getEdition());
        book.setQuantity(bookDto.getQuantity());
        book.setBorrowed(bookDto.getBorrowed());
        book.setAvailable(bookDto.getAvailable());

        when(bookRepository.findByTitle(bookDto.getTitle())).thenReturn(book);

        LibraryResponseDto bk = bookServiceImpl.createBook(bookDto);
        assertNotNull(bk.getTimestamp());
        assertEquals(genre, bk.getBook().getGenre());
        assertEquals(book, bk.getBook());
        assertEquals(302, bk.getStatus());
        assertEquals("Found", bk.getDescription());
        assertEquals("There is book with the title: " + bookDto.getTitle(), bk.getMessage());
        verify(iGenreService,times(0)).fetchGenreByName(bookDto.getGenre());

    }
}