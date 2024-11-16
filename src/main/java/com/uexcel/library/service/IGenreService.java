package com.uexcel.library.service;

import com.uexcel.library.dto.ResponseDto;

public interface IGenreService {

    ResponseDto fetchGenreByName(String genreName);
}
