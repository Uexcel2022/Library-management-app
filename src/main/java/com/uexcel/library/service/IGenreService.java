package com.uexcel.library.service;

import com.uexcel.library.model.Genre;

public interface IGenreService {

    Genre fetchGenreByName(String genreName);

}
