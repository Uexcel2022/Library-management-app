package com.uexcel.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class libraryResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseDto responseDto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Book book;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Genre genre;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Book> books;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
}

