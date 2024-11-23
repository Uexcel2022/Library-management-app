package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Genre;
import com.uexcel.library.dto.LibraryResponseDto;
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
    public LibraryResponseDto fetchGenreByName(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Genre %s not found.", genreName)));

        LibraryResponseDto lb = new LibraryResponseDto();
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setGenre(genre);
        return lb;
    }

}
