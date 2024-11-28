package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.BookRent;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.*;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.RentBookMapper;
import com.uexcel.library.repositoty.BookRentRepository;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.repositoty.LibraryUserRepository;
import com.uexcel.library.service.IRentBookService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.uexcel.library.service.IBookService.getTime;
import static com.uexcel.library.service.IBookService.validateBookNotNull;
import static com.uexcel.library.service.ILibraryUserService.validateUserNotNull;

@Service
@AllArgsConstructor
public class IRentBookImpl implements IRentBookService {
    private final BookRentRepository bookRentRepository;
    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;
    /**
     * @param bookRentRequestDto - will contain book rent info
     * @return will return status and message
     */
    @Override
    @Transactional
    public BookRentDto createBookRentDetails(BookRentRequestDto bookRentRequestDto) {
        if(bookRentRequestDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        if(bookRentRequestDto.getQuantity() > 1){
            throw new BadRequestException("User can only rent a copy of a book at a time.");
        }

        LibraryUser lib = libraryUserRepository.findByPhoneNumber(bookRentRequestDto.getPhoneNumber());

        validateUserNotNull(lib,bookRentRequestDto.getPhoneNumber());

        Book bk = bookRepository.findByTitleAndAuthor(bookRentRequestDto.getTitle(), bookRentRequestDto.getAuthor());

        validateBookNotNull(bk, bookRentRequestDto.getTitle(), bookRentRequestDto.getAuthor());

        bk.setAvailable(bk.getAvailable() - bookRentRequestDto.getQuantity());
        bk.setBorrowed(bk.getBorrowed() + bookRentRequestDto.getQuantity());


        BookRent bookRent = bookRentRepository
                .findByLibraryUserIdAndBookIdAndReturned(lib.getId(), bk.getId(),false);

        if (bookRent != null) {
            throw new BadRequestException(
                    String.format("You a have running rent on this book title: %s and author: %s",
                            bookRentRequestDto.getTitle(), bookRentRequestDto.getAuthor())
            );
        }

        BookRent rb = RentBookMapper.mapToRentBook(bookRentRequestDto,new BookRent());
        rb.setAmount(bk.getPrice());
        rb.setPaid(true);
        rb.setReturned(false);
        rb.setAmount(bk.getPrice());
        rb.setBook(bk);
        rb.setLibraryUser(lib);

        BookRent rt = bookRentRepository.save(rb);

        BookRentDto rdt = RentBookMapper.mapToRentBookDto(rt, new BookRentDto());
        return rdt;

    }

    @Override
    public ResponseDto returnBook(UserBookDto userBookDto) {
        if(userBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        LibraryUser lbu = libraryUserRepository.findByPhoneNumber(userBookDto.getPhoneNumber());

        Book bk = bookRepository.findByTitleAndAuthor(userBookDto.getTitle(),userBookDto.getAuthor());
         validateBookNotNull(bk,userBookDto.getTitle(),userBookDto.getAuthor());

        BookRent bookRent = bookRentRepository
                .findByLibraryUserIdAndBookIdAndReturned(lbu.getId(),
                        bk.getId(),false);

        if (bookRent == null) {
            throw new BadRequestException(
                    String.format("You do not have a running rent on this book title: %s and author: %s",
                            userBookDto.getTitle(),userBookDto.getAuthor())
            );
        }
        bookRent.setReturned(true);
        bk.setBorrowed(bk.getBorrowed()- bookRent.getQuantity());
        bk.setAvailable(bk.getAvailable()+ bookRent.getQuantity());
        bookRent.setBook(bk);
        bookRent.setLibraryUser(lbu);
        bookRentRepository.save(bookRent);

        ResponseDto rsp = new ResponseDto();
        rsp.setStatus(200);
        rsp.setDescription("Ok");
        rsp.setMessage("Book rent details updated successfully.");
        rsp.setApiPath("uri=/api/return");
        return rsp;
    }

    @Override
    @Transactional
    public ResponseDto deleteRentBook(UserBookDto userBookDto, String resourceName) {

        if(userBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }
        Book bk = bookRepository.findByTitleAndAuthor(userBookDto.getTitle(),userBookDto.getAuthor());

         validateBookNotNull(bk,userBookDto.getTitle(),userBookDto.getAuthor());

        List<BookRent> rb = bookRentRepository.findByBookId(bk.getId());

        if (rb.isEmpty()) {
            throw new ResourceNotFoundException(
                    resourceName+" not found given input data bookId" + bk.getId());
        }
        List<BookRent> runningRent = rb.stream().filter(vr->vr.isReturned()==false).toList();

        if (!runningRent.isEmpty()) {
           throw  new BadRequestException (resourceName+" could not be deleted because it has running rent.");
        }
        bookRentRepository.deleteByBook((bk));
        ResponseDto rdt = new ResponseDto();
        rdt.setStatus(200);
        rdt.setDescription("Ok");
        rdt.setMessage(resourceName+ " deleted successfully.");
        return rdt;
    }


    public List<BookRentDto> fetchRentBook(String bookId, String phoneNumber, boolean returned, String rentId) {

        List<BookRentDto> bookRentDtoList = new ArrayList<>();

        if(rentId != null){
          BookRent br = bookRentRepository.findById(rentId)
                  .orElseThrow(()->new ResourceNotFoundException("Rent not found given input data rentId: "+ rentId));
          if(!br.isReturned()){
              throw new BadRequestException("Rent details could not be deleted because it is running.");
          }
          bookRentRepository.delete(br);
        }


        List<BookRent> rents = bookRentRepository.findAll();

        if (rents.isEmpty()) {
            throw new ResourceNotFoundException("There is no book rent details available.");
        }

        if(bookId==null && bookId==null  && returned) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(bookId!=null && returned) {
          List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                  .equals(bookId) && vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(phoneNumber!=null && returned) {
            List<BookRent> rt = rents.stream()
                    .filter(vr-> vr.getLibraryUser()
                            .getPhoneNumber().equals(phoneNumber)&&
                            vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(bookId!=null && !returned) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && !vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(phoneNumber != null && !returned) {
            List<BookRent> rt = rents.stream()
                    .filter(vr-> vr.getLibraryUser()
                            .getPhoneNumber().equals(phoneNumber)&& !vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(bookId!=null && phoneNumber != null) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && vr.getLibraryUser().getPhoneNumber().equals(phoneNumber)).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }

        if(bookId!=null && phoneNumber != null && returned) {
            List<BookRent> rt = rents.stream().filter(vr-> vr.getBook().getId()
                    .equals(bookId) && vr.getLibraryUser()
                    .getPhoneNumber().equals(phoneNumber) && vr.isReturned()).toList();
            if (rt.isEmpty()) {
                throw new BadRequestException("There is no book rent details available.");
            }
            rt.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
            return bookRentDtoList;
        }


        rents.forEach(vr->bookRentDtoList.add(RentBookMapper.mapToRentBookDto(vr,new BookRentDto())));
        return bookRentDtoList;
    }


    private  LibraryResponseDto getResponse (LibraryResponseDto lb){
        lb.setTimestamp(getTime());
        lb.setStatus(200);
        lb.setDescription("Ok");
        if(lb.getRentedBooks().isEmpty()){
            lb.setMessage("There is no rent books details available.");
        }
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
