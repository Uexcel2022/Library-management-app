package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.mapper.BookMapper;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IGenreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IBookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final IGenreService genreService;
    private final Logger logger = LoggerFactory.getLogger(IBookServiceImpl.class);
    /**
     * @param bookDto - will hold book properties
     * @return - will hold response information
     */
    @Override
    public LibraryResponseDto createBook(BookDto bookDto) {
        Book bk = bookRepository.findByTitle(bookDto.getTitle());
        LibraryResponseDto lb;
        if (bk != null) {
            lb = new LibraryResponseDto();
            lb.setTimestamp(IBookService.getTime());
            lb.setStatus(302);
            lb.setDescription("Found");
            lb.setMessage("There is book with the title: " + bookDto.getTitle());
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
        return lb;
    }

}
