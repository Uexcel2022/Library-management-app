package com.uexcel.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {
    private String title;
    private String author;
    private String isbn;
    private String language;
    private String edition;
    private String publishedDate;
    private String genre;
}
