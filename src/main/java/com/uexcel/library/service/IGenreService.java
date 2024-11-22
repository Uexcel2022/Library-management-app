package com.uexcel.library.service;

import com.uexcel.library.dto.LibraryResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IGenreService {

    LibraryResponseDto fetchGenreByName(String genreName);

    default String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
