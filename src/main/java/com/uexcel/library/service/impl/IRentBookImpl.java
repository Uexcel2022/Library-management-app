package com.uexcel.library.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.BookRent;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.RentBookMapper;
import com.uexcel.library.repositoty.RentBookRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IRentBookService;
import com.uexcel.library.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uexcel.library.service.IBookService.getTime;

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
    public LibraryResponseDto createBookRentDetails(RentBookDto rentBookDto) {
        if(rentBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        if(rentBookDto.getQuantity() > 1){
            throw new BadRequestException("User can only rent one copy at a time.");
        }

        LibraryResponseDto lbu = iUserService.fetchUser(rentBookDto.getPhoneNumber());
        LibraryResponseDto lb =
                iBookService.fetchBook(rentBookDto.getTitle(),rentBookDto.getAuthor());

        BookRent bookRent = rentBookRepository
                .findByLibraryUserAndBookAndReturned(lbu.libraryUser,
                        lb.getBook(),false);
        if (bookRent != null) {
            throw new BadRequestException(
                    String.format("You have running rent on this book title: %s and author: %s",
                            rentBookDto.getTitle(),rentBookDto.getAuthor())
            );
        }

        Book bk = lb.getBook();
        bk.setBorrowed(bk.getBorrowed()+rentBookDto.getQuantity());
        bk.setAvailable(bk.getAvailable()-rentBookDto.getQuantity());
        BookRent rb = RentBookMapper.mapToRentBook(rentBookDto,new BookRent());
        rb.setAmount(bk.getPrice());
        rb.setPaid(true);
        rb.setBook(bk);
        rb.setLibraryUser(lbu.libraryUser);
        BookRent rt = rentBookRepository.save(rb);
        lb.setBook(null);
        RentBookDto rdt =RentBookMapper.mapToRentBookDto(rt,rentBookDto);
        rdt.setUserName(lbu.libraryUser.getFirstName() + " " + lbu.libraryUser.getLastName());
        lb.setStatus(201);
        lb.setDescription("Created");
        lb.setMessage("Book rent details created successfully.");
        lb.setApiPath("uri=/api/rent");
        lb.setRentBook(rdt);
        return lb;

    }

    @Override
    public LibraryResponseDto returnBook(UserBookDto userBookDto) {
        if(userBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        LibraryResponseDto lbu = iUserService.fetchUser(userBookDto.getPhoneNumber());
        LibraryResponseDto lb =
                iBookService.fetchBook(userBookDto.getTitle(),userBookDto.getAuthor());


        BookRent bookRent = rentBookRepository
                .findByLibraryUserAndBookAndReturned(lbu.libraryUser,
                        lb.getBook(),false);

        if (bookRent == null) {
            throw new BadRequestException(
                    String.format("You do not have a running rent on this book title: %s and author: %s",
                            userBookDto.getTitle(),userBookDto.getAuthor())
            );
        }
        bookRent.setReturned(true);
        Book bk = lb.getBook();
        bk.setBorrowed(bk.getBorrowed()- bookRent.getQuantity());
        bk.setAvailable(bk.getAvailable()+ bookRent.getQuantity());
        bookRent.setBook(bk);
        bookRent.setLibraryUser(lbu.libraryUser);
        rentBookRepository.save(bookRent);

        lb.setBook(null);
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setMessage("Book rent details updated successfully.");
        lb.setApiPath("uri=/api/return");
        return lb;
    }

    @Override
    @Transactional
    public LibraryResponseDto deleteRentBook(UserBookDto userBookDto, String resourceName) {

        if(userBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        LibraryResponseDto lb = iBookService.
                fetchBook(userBookDto.getTitle(),userBookDto.getAuthor());
        List<BookRent> bk = rentBookRepository.findByBook(lb.getBook());
        if (bk.isEmpty()) {
            throw new ResourceNotFoundException(
                    resourceName+" not found given input data bookId" + lb.getBook().getId());
        }
        List<BookRent> runningRent = bk.stream().filter(vr->vr.isReturned()==false).toList();

        if (!runningRent.isEmpty()) {
           throw  new BadRequestException (resourceName+" could not be deleted because it has running rent.");
        }

        rentBookRepository.deleteByBook(lb.getBook());
        lb.setBook(null);
        return setResponse(lb);
    }


    @Override
    public LibraryResponseDto deleteRentBook(String id) {
        List<BookRent> bookRents = rentBookRepository.findAll();
        LibraryResponseDto lb = new LibraryResponseDto();
        if(!bookRents.isEmpty() && id == null) {
            lb.setTimestamp(getTime());
            lb.setStatus(200);
            lb.setDescription("Ok");
            lb.setRentedBooks(bookRents);
            return setResponse(lb);
        }

        if(!bookRents.isEmpty() && id != null ) {
            rentBookRepository.deleteById(id);
            lb.setTimestamp(getTime());
            lb.setStatus(200);
            lb.setDescription("Ok");
            lb.setRentedBooks(rentBookRepository.findAll());
            return setResponse(lb);
        }

        throw  new  ResourceNotFoundException("There is no book rent details available.");

    }

    public LibraryResponseDto fetchRentBook(String bookId,String phoneNumber, boolean returned) {

        LibraryResponseDto lb = new LibraryResponseDto();
        List<BookRent> rents = rentBookRepository.findAll();
        if (rents.isEmpty()) {
            throw new ResourceNotFoundException("There is no book rent details available.");
        }

        if(bookId!=null && returned) {
          List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                  .equals(bookId) && vr.isReturned()).toList();
          lb.setRentedBooks(rt);
          return getResponse(lb);
        }

        if(phoneNumber!=null && returned) {
            List<BookRent> rt = rents.stream()
                    .filter(vr-> vr.getLibraryUser()
                            .getPhoneNumber().equals(phoneNumber)&&
                            vr.isReturned()).toList();
            lb.setRentedBooks(rt);
            return getResponse(lb);
        }

        if(bookId!=null && !returned) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && !vr.isReturned()).toList();
            lb.setRentedBooks(rt);
            return getResponse(lb);
        }

        if(phoneNumber != null && !returned) {
            List<BookRent> rt = rents.stream()
                    .filter(vr-> vr.getLibraryUser()
                            .getPhoneNumber().equals(phoneNumber)&& !vr.isReturned()).toList();
            lb.setRentedBooks(rt);
            return getResponse(lb);
        }

        if(bookId!=null && phoneNumber != null) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && vr.getLibraryUser().getPhoneNumber().equals(phoneNumber)).toList();
            lb.setRentedBooks(rt);
            return getResponse(lb);
        }

        if(bookId!=null && phoneNumber != null && returned) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && vr.getLibraryUser()
                    .getPhoneNumber().equals(phoneNumber) && vr.isReturned()).toList();
            lb.setRentedBooks(rt);
            return getResponse(lb);
        }

        lb.setRentedBooks(rents);
        return getResponse(lb);
    }


    private  LibraryResponseDto getResponse (LibraryResponseDto lb){
        lb.setTimestamp(getTime());
        lb.setStatus(200);
        lb.setDescription("Ok");
        return lb;
    }


    private LibraryResponseDto setResponse(LibraryResponseDto lb) {
        lb.setBook(null);
        lb.setTimestamp(getTime());
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setMessage("Book rent details deleted successfully.");
        lb.setApiPath("uri=/api/delete-rent");
        return lb;

    }

}
