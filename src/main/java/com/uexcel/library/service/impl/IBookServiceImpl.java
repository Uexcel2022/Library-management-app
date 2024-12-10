package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.Genre;
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
import org.springframework.http.HttpStatus;
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

        checkBookExist(bookRequestDto.getTitle(), bookRequestDto.getAuthor());
        
        ResponseDto rs = new ResponseDto();
        
         Genre genre = genreService.fetchGenreByName(bookRequestDto.getGenre());

        Book book = BookMapper.mapToNewBook(bookRequestDto, new Book());
        book.setGenre(genre);
        bookRepository.save(book);
        rs.setStatus(HttpStatus.CREATED.value());
        rs.setDescription(HttpStatus.CREATED.getReasonPhrase());
        rs.setMessage("Book created successfully.");
        return rs;
    }

    @Override
    public BookDto fetchBook(String title, String author) {

        Book book = bookRepository.findByTitleAndAuthor(title,author);

        IBookService.validateBookNotNull(book,title,author);

        return BookMapper.mapToBookDto(book,new BookDto());
    }
    

    @Override
    public ResponseDto updateBook(BookDto bookDto) {
        Book bk = null;
        if (bookDto != null && bookDto.getId() != null) {
            bk = bookRepository.findById(bookDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                                    "Book not found given input data bookId: " + bookDto.getId())
                    );
        }
        if(!bk.getAuthor().equalsIgnoreCase(bookDto.getAuthor())||
                !bk.getTitle().equalsIgnoreCase(bookDto.getTitle())){
            checkBookExist(bookDto.getTitle(), bookDto.getAuthor());

        }
        bookRepository.save(BookMapper.mapToUpdateBook(bookDto,bk));
        ResponseDto rsp = new ResponseDto();
        rsp.setStatus(HttpStatus.OK.value());
        rsp.setDescription(HttpStatus.OK.getReasonPhrase());
        rsp.setMessage("Book updated successfully.");
        return  rsp;
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
    public ResponseDto deleteBook(String title, String author, String bookId) {

        if(title == null && author == null && bookId == null) {
            throw new BadRequestException("Input can not be null");
        }

        if(bookId == null || bookId.isEmpty()) {
            Book book = bookRepository.findByTitleAndAuthor(title, author);
            IBookService.validateBookNotNull(book, title, author);
            bookId = book.getId();
        }
       return deleteUserBookRentService
               .deleteRentBook(bookId,"Book");

    }
    
    private void checkBookExist(String title, String author) {
        
        Book bk = bookRepository.findByTitleAndAuthor(title, author);
        
        if (bk != null) {
            throw new BadRequestException(
                    String.format("A book has same title: %s and author: %s", title, author)
            );
        }
    }

}
