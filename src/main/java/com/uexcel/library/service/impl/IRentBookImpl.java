package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.RentBook;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.mapper.RentBookMapper;
import com.uexcel.library.repositoty.RentBookRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IRentBookService;
import com.uexcel.library.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IRentBookImpl implements IRentBookService {
    private final RentBookRepository rentBookRepository;
    private IBookService iBookService;
    private IUserService iUserService;
    /**
     * @param rentBookDto - will contain book rent info
     * @return will return status and message
     */
    @Override
    @Transactional
    public LibraryResponseDto createRentBook(RentBookDto rentBookDto) {
        if(rentBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        if(rentBookDto.getQuantity() > 1){
            throw new BadRequestException("User can only rent one copy at a time.");
        }

        LibraryResponseDto lbu = iUserService.fetchUser(rentBookDto.getPhoneNumber());
        LibraryResponseDto lb =
                iBookService.fetchBook(rentBookDto.getBootTile(),rentBookDto.getAuthor());

        RentBook rentBook = rentBookRepository
                .findByLibraryUserAndBookAndReturned(lbu.libraryUser,
                        lb.getBook(),false);
        if (rentBook != null) {
            throw new BadRequestException(
                    String.format("You have running rent on this book title: %s and author: %s",
                            rentBook.getBook().getTitle(),rentBook.getBook().getAuthor())
            );
        }

        Book bk = lb.getBook();
        bk.setBorrowed(bk.getBorrowed()+rentBookDto.getQuantity());
        bk.setAvailable(bk.getAvailable()-rentBookDto.getQuantity());
        RentBook rb = RentBookMapper.mapToRentBook(rentBookDto,new RentBook());
        rb.setAmount(bk.getPrice());
        rb.setPaid(true);
        rb.setBook(bk);
        rb.setLibraryUser(lbu.libraryUser);
        RentBook rt = rentBookRepository.save(rb);
        lb.setBook(null);
        RentBookDto rdt =RentBookMapper.mapToRentBookDto(rt,rentBookDto);
        rdt.setUserName(lbu.libraryUser.getFirstName() + " " + lbu.libraryUser.getLastName());
        lb.setStatus(201);
        lb.setDescription("Created");
        lb.setMessage("Request processed successfully.");
        lb.setApiPath("uri=/api/rent");
        lb.setRentBookDto(rdt);
        return lb;

    }

    @Override
    public LibraryResponseDto returnBook(RentBookDto rentBookDto) {
        if(rentBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        LibraryResponseDto lbu = iUserService.fetchUser(rentBookDto.getPhoneNumber());
        LibraryResponseDto lb =
                iBookService.fetchBook(rentBookDto.getBootTile(),rentBookDto.getAuthor());

        RentBook rentBook = rentBookRepository
                .findByLibraryUserAndBookAndReturned(lbu.libraryUser,
                        lb.getBook(),false);
        if (rentBook == null) {
            throw new BadRequestException(
                    String.format("You do not have a running rent on this book title: %s and author: %s",
                            rentBook.getBook().getTitle(),rentBook.getBook().getAuthor())
            );
        }
        rentBook.setReturned(true);
        Book bk = lb.getBook();
        bk.setBorrowed(bk.getBorrowed()-rentBookDto.getQuantity());
        bk.setAvailable(bk.getAvailable()+rentBookDto.getQuantity());
        rentBook.setBook(bk);
        rentBook.setLibraryUser(lbu.libraryUser);
        rentBookRepository.save(rentBook);

        lb.setBook(null);
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setMessage("Request processed successfully.");
        lb.setApiPath("uri=/api/return");
        return lb;
    }
}
