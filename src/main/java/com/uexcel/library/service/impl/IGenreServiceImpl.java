package com.uexcel.library.service.impl;

import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.repositoty.GenreRepository;
import com.uexcel.library.service.IGenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IGenreServiceImpl implements IGenreService {
    private final GenreRepository genreRepository;
    @Override
    public ResponseDto fetchGenreByName(String genreName) {
        return null;
    }
}
