package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.BookMapper;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IGenreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class IBookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final IGenreService genreService;
    private final DeleteUserBookRentService deleteUserBookRentService;
    private final Logger logger = LoggerFactory.getLogger(IBookServiceImpl.class);
    /**
     * @param bookDto - will hold book properties
     * @return - will hold response information
     */
    @Override
    public LibraryResponseDto createBook(BookDto bookDto) {
        Book bk = bookRepository.findByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        LibraryResponseDto lb;
        if (bk != null) {
            lb = new LibraryResponseDto();
            lb.setTimestamp(IBookService.getTime());
            lb.setStatus(302);
            lb.setDescription("Found");
            lb.setMessage(String.format("There is book with the title: %s  and author: %s ",
                    bookDto.getTitle(), bookDto.getAuthor()));
            lb.setBook(bk);
            lb.setApiPath("uri=/api/create-book");
            logger.debug("IBookServiceImpl.createBook: Book exists Id: {}",bk.getId());
            return lb;
        }

        lb = genreService.fetchGenreByName(bookDto.getGenre());

        Book book = BookMapper.mapToBook(bookDto, new Book());
        book.setGenre(lb.getGenre());
        bookRepository.save(book);
        lb.setGenre(null);
        lb.setStatus(201);
        lb.setDescription("Created");
        lb.setMessage("Book created successfully.");
        lb.setApiPath("uri=/api/create-book");
        return lb;
    }

    public LibraryResponseDto fetchBook(String title, String author) {
        Book book = bookRepository.findByTitleAndAuthor(title,author);
        LibraryResponseDto lb = new LibraryResponseDto();
        if(book == null) {
            throw new ResourceNotFoundException(
                    String.format("Book with title: %s and author: %s not found.", title,author));
        }
        lb.setStatus(200);
        lb.setDescription("ok");
        lb.setBook(book);
        lb.setApiPath("uri=/api/fetch-book");
        return lb;
    }

    @Override
    public LibraryResponseDto updateBook(BookDto bookDto) {
        return null;
    }



    @Override
    public LibraryResponseDto fetchAllBooks(String author, String genre) {
        List<Book> books = bookRepository.findAll();
        List<Book> booklist;

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
        LibraryResponseDto lb = new LibraryResponseDto();
        lb.setTimestamp(IBookService.getTime());
        lb.setStatus(200);
        lb.setDescription("ok");
        lb.setBooks(booklist);
        lb.setApiPath("uri=/api/fetch-all-books");
        return lb;
    }

    @Override
    public LibraryResponseDto deleteBook(UserBookDto userBookDto) {
       return deleteUserBookRentService
               .deleteRentBook(userBookDto,"Book","uri=/api/delete-book");

    }

}
