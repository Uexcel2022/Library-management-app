package com.uexcel.library.service.impl;

import com.uexcel.library.model.Genre;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.repositoty.GenreRepository;
import com.uexcel.library.service.IGenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class IGenreServiceImpl implements IGenreService {
    private final GenreRepository genreRepository;
    @Override
    public Genre fetchGenreByName(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Genre not found given input data genreName: %s", genreName)));

        return genre;
    }

}
