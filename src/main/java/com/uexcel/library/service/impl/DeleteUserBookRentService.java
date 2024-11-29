package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.BookRent;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.repositoty.BookRepository;
import com.uexcel.library.repositoty.BookRentRepository;
import com.uexcel.library.repositoty.LibraryUserRepository;
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
    private final BookRentRepository bookRentRepository;
    private final LibraryUserRepository libraryUserRepository;

    private final Logger logger = LoggerFactory.getLogger(DeleteUserBookRentService.class);

    @Transactional
    public ResponseDto deleteRentBook(String resourceId, String resourceName, String apiPath) {
        if(resourceId == null){
            throw new BadRequestException("Input can not be null.");
        }

        List<BookRent> rb;

        if("Book".equals(resourceName)) {
            Book bk = bookRepository.findById(resourceId).orElseThrow( ()->
                new ResourceNotFoundException(String.format("Book not found given input data bookId: %s ",resourceId)));

           rb = bookRentRepository.findByBook(bk);

            if (rb.isEmpty()) {
                bookRepository.delete(bk);
                return getResponse(apiPath);
            }
            checkForRunningRent(rb,resourceName);
            bookRentRepository.deleteByBook(bk);
            bookRepository.delete(bk);
            return getResponse(apiPath);
        }

        if("User".equals(resourceName)) {
            LibraryUser user = libraryUserRepository.findById(resourceId)
                    .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("User not found given input data userId: %s", resourceId))
                    );
            rb = bookRentRepository.findByLibraryUser(user);
            if (rb.isEmpty()) {
                libraryUserRepository.delete(user);
                return getResponse(apiPath);
            }
            checkForRunningRent(rb,resourceName);
            bookRentRepository.deleteByLibraryUser(user);
            libraryUserRepository.delete(user);
            return getResponse(apiPath);
        }

        logger.debug("User or Book if block are not being executed!!! Pass in correct resource name: User or Book.");
        return fail(apiPath);
    }

    private ResponseDto getResponse(String apiPath) {
        ResponseDto resp = new ResponseDto();
        resp.setTimestamp(getTime());
        resp.setStatus(200);
        resp.setDescription("Ok");
        resp.setMessage("Deleted successfully.");
        resp.setApiPath(apiPath);
        return resp;
    }

    private ResponseDto fail(String apiPath) {
        ResponseDto resp = new ResponseDto();
        resp.setTimestamp(getTime());
        resp.setStatus(417);
        resp.setDescription("Fail");
        resp.setMessage("Deletion failed.");
        resp.setApiPath(apiPath);
        return resp;
    }


    private void checkForRunningRent(List<BookRent> bookRents, String resourceName){
        List<BookRent> runningRent = bookRents.stream().filter(vr->!vr.isReturned()).toList();

        if (!runningRent.isEmpty()) {
            throw  new BadRequestException (resourceName +" could not be deleted because of the running rent.");
        }
    }


}
