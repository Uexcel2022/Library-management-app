package com.uexcel.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.Genre;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.BookRent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter

public class LibraryResponseDto extends ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Book book;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Genre genre;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Book> books;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LibraryUser libraryUser;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<LibraryUser> libraryUsers;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BookRent rentBook;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookRent> rentBooks;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BookRent> rentedBooks;
}
