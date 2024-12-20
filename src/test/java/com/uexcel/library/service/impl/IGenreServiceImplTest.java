package com.uexcel.library.service.impl;

import com.uexcel.library.model.Genre;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.repositoty.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class IGenreServiceImplTest {

    @InjectMocks
    private IGenreServiceImpl gServiceImpl;

    @Mock
    private GenreRepository genreRepository;

    private Genre genre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre = new Genre();

    }


    @Test
    public void fetchGenreTest() {
        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());

      when(genreRepository.findByGenreName("thriller")).thenReturn(Optional.of(genre));
      Genre g = gServiceImpl.fetchGenreByName("thriller");

     assertEquals(g.getGenreName(), genre.getGenreName());
     assertEquals(g.getId(), genre.getId());


    }

    @Test
    public void fetchGenreNotFoundTest() {

        genre.setGenreName("thriller");
        genre.setId(UUID.randomUUID().toString());


        when(genreRepository.findByGenreName("thriller")).thenReturn(Optional.of(genre));

      String ex = assertThrows(ResourceNotFoundException.class,() -> gServiceImpl.fetchGenreByName("thrille")).getMessage();
      assertEquals("Genre not found given input data genreName: thrille",ex);

    }



}