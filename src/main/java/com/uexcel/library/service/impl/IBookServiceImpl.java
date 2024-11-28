package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.*;

import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.BookMapper;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IGenreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class IBookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final IGenreService genreService;
    private final DeleteUserBookRentService deleteUserBookRentService;
    private final Logger logger = LoggerFactory.getLogger(IBookServiceImpl.class);
    /**
     * @param bookRequestDto - will hold book properties
     * @return - will hold response information
     */
    @Override
    public ResponseDto createBook(BookRequestDto bookRequestDto) {
        Book bk = bookRepository.findByTitleAndAuthor(bookRequestDto.getTitle(), bookRequestDto.getAuthor());
        ResponseDto rs = new ResponseDto();

        if (bk != null) {
            throw new BadRequestException(
                    String.format("A book has same title: %s and author: %s",
                            bookRequestDto.getTitle(), bookRequestDto.getTitle())
            );
        }

         LibraryResponseDto lb = genreService.fetchGenreByName(bookRequestDto.getGenre());

        Book book = BookMapper.mapToNewBook(bookRequestDto, new Book());
        book.setGenre(lb.getGenre());
        bookRepository.save(book);
        rs.setStatus(201);
        rs.setDescription("Created");
        rs.setMessage("Book created successfully.");
        rs.setApiPath("uri=/api/create-book");
        return rs;
    }

    @Override
    public BookDto fetchBook(String title, String author) {

        Book book = bookRepository.findByTitleAndAuthor(title,author);

        IBookService.validateBookNotNull(book,title,author);

        return BookMapper.mapToBookDto(book,new BookDto());
    }

    @Override
    public ResponseDto updateBook(BookRequestDto bookRequestDto) {
        return null;
    }



    @Override
    public List<BookDto> fetchAllBooks(String author, String genre) {
        List<Book> books = bookRepository.findAll();
        List<Book> booklist;


        if(!books.isEmpty() && genre != null && author != null) {
            booklist = books.stream()
                    .filter(vr -> vr.getGenre().getGenreName().equals(genre) && vr.getAuthor().equals(author)).toList();
        } else

        if(!books.isEmpty() && genre != null) {
            booklist = books.stream()
                    .filter(vr->vr.getGenre().getGenreName().equals(genre)).toList();
        }else {
            if (!books.isEmpty() && author != null) {
                booklist = books.stream()
                        .filter(vr -> vr.getAuthor().equals(author)).toList();
            }else {
                booklist = books;
            }
        }
        if(booklist.isEmpty()) {
            throw new ResourceNotFoundException("No book details found.");
        }
        List<BookDto> bookDtoList = new ArrayList<>();
        booklist.forEach(vr -> bookDtoList.add(BookMapper.mapToBookDto(vr,new BookDto())));
        return bookDtoList ;
    }

    @Override
    public LibraryResponseDto deleteBook(String title, String author) {
        UserBookDto ubd = new UserBookDto();
        ubd.setTitle(title);
        ubd.setAuthor(author);
       return deleteUserBookRentService
               .deleteRentBook(ubd,"Book","uri=/api/delete-book");

    }

}
