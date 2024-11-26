package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.dto.LibraryResponseDto;
import com.uexcel.library.dto.LibraryUserDto;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.UserMapper;
import com.uexcel.library.repositoty.UserRepository;
import com.uexcel.library.service.IBookService;
import com.uexcel.library.service.IUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final DeleteUserBookRentService deleteUserBookRentService;
    private final Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);
    /**
     * @param lud - contains user information
     * @return status and message
     */
    @Override
    public LibraryResponseDto createUser(LibraryUserDto lud) {
        LibraryUser inDBUser = userRepository.
                findByPhoneNumberOrEmail(lud.getPhoneNumber(), lud.getEmail());
        LibraryResponseDto lb = new LibraryResponseDto();
        if(inDBUser != null){
            lb.setTimestamp(IBookService.getTime());
            lb.setStatus(302);
            lb.setDescription("Found");
            if(inDBUser.getEmail().equals(lud.getEmail()) && inDBUser.getPhoneNumber().equals(lud.getPhoneNumber())) {
                lb.setMessage(String.format("There is a user with email: %s and phone number: %s",
                        lud.getEmail(), lud.getPhoneNumber()));
            }else {
                if (inDBUser.getEmail().equals(lud.getEmail())) {
                    lb.setMessage(String.format("There is a user with email address: %s", lud.getEmail()));
                }else {
                    lb.setMessage(String.format("There is a user with phone number: %s",lud.getPhoneNumber()));
                }
            }
            lb.setLibraryUser(inDBUser);
            lb.setApiPath("uri=/api/create-user");
            logger.debug("IUserServiceImpl.createUser: user exists Id: {}", inDBUser.getId());
            return lb;
        }
        userRepository.save(UserMapper.mapToUser(lud,new LibraryUser()));

        lb.setStatus(201);
        lb.setDescription("Created");
        lb.setMessage("User created successfully.");
        lb.setApiPath("uri=/api/create-user");
        return lb;
    }

    @Override
    public LibraryResponseDto fetchUser(String emailOrPhoneNumber) {
        LibraryUser lUser = userRepository.
                findByPhoneNumberOrEmail(emailOrPhoneNumber, emailOrPhoneNumber);
        LibraryResponseDto lb = new LibraryResponseDto();
        if(lUser == null) {
            throw new ResourceNotFoundException(
                    String.format("User with email or phone number: %s not found.", emailOrPhoneNumber)
            );
        }
        lb.setStatus(200);
        lb.setDescription("Ok");
        lb.setLibraryUser(lUser);
        lb.setApiPath("uri=/api/fetch-user");
        return lb;
        }

    /**
     * @param userBookDto - will hold information for deleting user
     * @return - response status and message
     */
    @Override
    public LibraryResponseDto deleteUser(UserBookDto userBookDto) {
        return deleteUserBookRentService
                .deleteRentBook(userBookDto,"User","uri=/api/delete-user");
    }
}
