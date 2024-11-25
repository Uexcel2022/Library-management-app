package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.RentBook;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.RentBookDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.repositoty.RentBookRepository;
import com.uexcel.library.repositoty.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.uexcel.library.service.IBookService.getTime;

@Component
@AllArgsConstructor
public class DeleteUserBookRentService {
    private final BookRepository bookRepository;
    private final RentBookRepository rentBookRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(DeleteUserBookRentService.class);

    @Transactional
    public LibraryResponseDto deleteRentBook(RentBookDto rentBookDto, String resourceName, String apiPath) {
        if(rentBookDto == null){
            throw new BadRequestException("Input can not be null.");
        }

        List<RentBook> rb;

        if("Book".equals(resourceName)) {

            Book bk = bookRepository.findByTitleAndAuthor(rentBookDto.getBookTile(), rentBookDto.getAuthor());
            if (bk == null) {
                throw new ResourceNotFoundException(String.format("Book with title: %s and book author: %s not found.",
                        rentBookDto.getBookTile(), rentBookDto.getAuthor()));
            }

           rb = rentBookRepository.findByBook(bk);

            if (rb.isEmpty()) {
                bookRepository.delete(bk);
                return getResponse(apiPath);
            }
            checkForRunningRent(rb,resourceName);
            rentBookRepository.deleteByBook(bk);
            bookRepository.delete(bk);
            return getResponse(apiPath);
        }

        if("User".equals(resourceName)) {
            LibraryUser user = userRepository.findByPhoneNumber(rentBookDto.getPhoneNumber());
            if (user == null) {
                throw new ResourceNotFoundException(String.format("User with phone number: %s not found."
                        ,rentBookDto.getPhoneNumber()));
            }
            rb = rentBookRepository.findByLibraryUser(user);
            if (rb.isEmpty()) {
                userRepository.delete(user);
                return getResponse(apiPath);
            }
            checkForRunningRent(rb,resourceName);
            rentBookRepository.deleteByLibraryUser(user);
            userRepository.delete(user);
            return getResponse(apiPath);
        }

        logger.debug("User or Book if block are not being executed!!! Pass in correct resource name: User or Book.");
        return fail(apiPath);
    }

    private LibraryResponseDto getResponse(String apiPath) {
        LibraryResponseDto lb = new LibraryResponseDto();
        lb.setTimestamp(getTime());
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setMessage("Deleted successfully.");
        lb.setApiPath(apiPath);
        return lb;
    }

    private LibraryResponseDto fail(String apiPath) {
        LibraryResponseDto lb = new LibraryResponseDto();
        lb.setTimestamp(getTime());
        lb.setStatus(417);
        lb.setDescription("Fail");
        lb.setMessage("Deletion failed.");
        lb.setApiPath(apiPath);
        return lb;
    }


    private void checkForRunningRent(List<RentBook> rentBooks, String resourceName){
        List<RentBook> runningRent = rentBooks.stream().filter(vr->vr.isReturned()==false).toList();

        if (!runningRent.isEmpty()) {
            throw  new BadRequestException (resourceName +" could not be deleted because of the running rent.");
        }
    }


}
