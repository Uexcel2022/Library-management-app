package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.RentBook;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
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

        LibraryResponseDto lbu = iUserService.fetchUser(rentBookDto.getPhoneNumber());
        LibraryResponseDto lb = iBookService.fetchBook(rentBookDto.getBootTile());

        RentBook rentBook = rentBookRepository
                .findByLibraryUserAndBookAndReturned(lbu.libraryUser,
                        lb.getBook(),false);
        if (rentBook != null) {
            throw new RuntimeException("RentBook already exist");
        }

        Book bk = lb.getBook();
        bk.setBorrowed(bk.getBorrowed()+rentBookDto.getQuantity());
        bk.setAvailable(bk.getAvailable()-rentBookDto.getQuantity());
        RentBook rb = RentBookMapper.mapToRentBook(rentBookDto,new RentBook());
        rb.setAmount(bk.getPrice());
        rb.setPaid(true);
        rb.setBook(bk);
        rb.setLibraryUser(lbu.libraryUser);
        rentBookRepository.save(rb);
        lb.setBook(null);
        lb.setStatus(201);
        lb.setDescription("Create");
        lb.setMessage("Process request successfully.");
        lb.setApiPath("uri=/api/fetch-user");
        return lb;

    }
}
