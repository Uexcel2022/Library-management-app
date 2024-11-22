package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Genre;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.repositoty.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class IGenreServiceImplTest {

    @InjectMocks
    private IGenreServiceImpl gServiceImpl;

    @Mock
    private GenreRepository genreRepository;

    private Genre genre;
    LibraryResponseDto lb;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre = new Genre();
        lb = new LibraryResponseDto();

    }


    @Test
    public void fetchGenreTest() {
        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());

      when(genreRepository.findByGenreName("thriller")).thenReturn(Optional.of(genre));
      LibraryResponseDto lb = gServiceImpl.fetchGenreByName("thriller");

     assertEquals(lb.getStatus(),200);
     assertEquals(lb.getDescription(),"Ok");
     assertEquals(lb.getGenre().getGenreName(), genre.getGenreName());
     assertEquals(lb.getGenre().getId(), genre.getId());


    }

    @Test
    public void fetchGenreNotFoundTest() {

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());

        lb.setTimestamp(getTime());
        lb.setStatus(404);
        lb.setDescription("Not Found");
        lb.setMessage("Genre not found given input data genreName: " + genre.getGenreName());
        lb.setApiPath("uri=/api/genre");


        when(genreRepository.findByGenreName("thriller")).thenReturn(Optional.of(genre));

      String ex = assertThrows(ResourceNotFoundException.class,() -> gServiceImpl.fetchGenreByName("thrille")).getMessage();
      assertEquals("Genre not found given input data genreName: thrille",ex);

    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }



}