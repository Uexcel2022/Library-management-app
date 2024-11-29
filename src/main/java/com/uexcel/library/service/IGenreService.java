package com.uexcel.library.service;

import com.uexcel.library.Entity.Genre;

public interface IGenreService {

    Genre fetchGenreByName(String genreName);

}
