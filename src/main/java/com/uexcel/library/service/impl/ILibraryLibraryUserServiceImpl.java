package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.ErrorResponseDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.UserMapper;
import com.uexcel.library.repositoty.LibraryUserRepository;
import com.uexcel.library.service.ILibraryUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ILibraryLibraryUserServiceImpl implements ILibraryUserService {
    private final LibraryUserRepository libraryUserRepository;
    private final DeleteUserBookRentService deleteUserBookRentService;
    private final Logger logger = LoggerFactory.getLogger(ILibraryLibraryUserServiceImpl.class);
    /**
     * @param lud - contains user information
     * @return status and message
     */
    @Override
    public ErrorResponseDto createUser(UserDto lud) {
        if (lud == null){
            throw new BadRequestException("Library user input is null.");
        }
        checkForExistUser(lud.getPhoneNumber(), lud.getEmail());

        ErrorResponseDto rsp = new ErrorResponseDto();
        libraryUserRepository.save(UserMapper.mapToUser(lud,new LibraryUser()));

        rsp.setStatus(HttpStatus.CREATED.value());
        rsp.setDescription(HttpStatus.CREATED.getReasonPhrase());
        rsp.setMessage("User created successfully.");
        return rsp;
    }

    @Override
    public UserDto fetchUser(String phoneNumber) {

        LibraryUser lUser = libraryUserRepository.findByPhoneNumber(phoneNumber);

        ILibraryUserService.validateUserNotNull(lUser,phoneNumber);

        return UserMapper.mapToUserDto(lUser,new UserDto());
        }

    /**
     * @param phoneNumber - will hold user phone number information
     * @return - response status and message
     */
    @Override
    public ResponseDto deleteUser(String phoneNumber) {
        if(!phoneNumber.matches("0[0-9][01][0-9]{8}")){
            throw new  BadRequestException(String.format("The phone number %s is invalid", phoneNumber));
        }
        LibraryUser lUser = libraryUserRepository.findByPhoneNumber(phoneNumber);
        ILibraryUserService.validateUserNotNull(lUser,phoneNumber);

        return deleteUserBookRentService
                .deleteRentBook(lUser.getId(),"User");
    }

    @Override
    public ResponseDto updateUser(UserDto lud) {
        if (lud == null){
            throw new BadRequestException("The library user input is null.");
        }

        if(lud.getId() == null || lud.getId().isEmpty()){
            throw new  BadRequestException("User id is not found.");
        }

        LibraryUser toUpdateUser = libraryUserRepository.findById(lud.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User not found given input data userId: %s", lud.getId()))
                );

        if(!toUpdateUser.getPhoneNumber().equals(lud.getPhoneNumber())){
            checkForExistUser(lud.getPhoneNumber(),"0");

        }

        if(!toUpdateUser.getEmail().equalsIgnoreCase(lud.getEmail())){
            checkForExistUser("0",lud.getPhoneNumber());
        }

        libraryUserRepository.save(UserMapper.mapToUser(lud, toUpdateUser));

        ResponseDto rsp = new ResponseDto();
        rsp.setStatus(HttpStatus.OK.value());
        rsp.setDescription(HttpStatus.OK.getReasonPhrase());
        rsp.setMessage("User updated successfully.");
        return rsp;
    }

    private void checkForExistUser(String phoneNumber, String email){
        LibraryUser inDBUserPhoneNumber = libraryUserRepository.findByPhoneNumber(phoneNumber);
        LibraryUser inDBUserEmail = libraryUserRepository.findByEmailIgnoreCase(email);

        if(inDBUserPhoneNumber != null && inDBUserEmail != null){
                throw new BadRequestException(
                        String.format("The the phone number %s and email address %s haven been used.",
                                inDBUserPhoneNumber.getPhoneNumber(),inDBUserEmail.getEmail())
                );
            }

            if(inDBUserPhoneNumber!= null){
                throw new BadRequestException(
                        String.format("The phone number %s has been used.", inDBUserPhoneNumber.getPhoneNumber())
                );

            }

            if(inDBUserEmail != null){
                throw new BadRequestException(
                        String.format("The email address %s has been used.", inDBUserEmail.getEmail())
                );
            }

    }
}
