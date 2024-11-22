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
    private int quantity;
    private double price;
    private int borrowed;
    private int available;
    private String genre;

}
