package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserBookDto;
import com.uexcel.library.dto.LibraryUserDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.ResourceNotFoundException;
import com.uexcel.library.mapper.UserMapper;
import com.uexcel.library.repositoty.LibraryUserRepository;
import com.uexcel.library.service.ILibraryUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseDto createUser(LibraryUserDto lud) {

        checkForExistUser(lud);
        ResponseDto rsp = new ResponseDto();
        libraryUserRepository.save(UserMapper.mapToUser(lud,new LibraryUser()));

        rsp.setStatus(201);
        rsp.setDescription("Created");
        rsp.setMessage("User created successfully.");
        rsp.setApiPath("uri=/api/create-user");
        return rsp;
    }

    @Override
    public LibraryUserDto fetchUser(String phoneNumber) {
        LibraryUser lUser = libraryUserRepository.findByPhoneNumber(phoneNumber);

        ILibraryUserService.validateUserNotNull(lUser,phoneNumber);

        return UserMapper.mapToUserDto(lUser,new LibraryUserDto());
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
                .deleteRentBook(lUser.getId(),"User","uri=/api/delete-user");
    }

    @Override
    public ResponseDto updateUser(LibraryUserDto libraryUserDto) {
        if (libraryUserDto == null){
            throw new BadRequestException("The library user is null.");
        }

        if(libraryUserDto.getId() == null || libraryUserDto.getId().isEmpty()){
            throw new  BadRequestException("User id is not found.");
        }

        LibraryUser lUser = libraryUserRepository.findById(libraryUserDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User not found given input data userId: %s", libraryUserDto.getId()))
                );

        if(!lUser.getPhoneNumber().equals(libraryUserDto.getPhoneNumber()) ||
                !lUser.getEmail().equalsIgnoreCase(libraryUserDto.getEmail())){

            checkForExistUser(libraryUserDto);

        }

        libraryUserRepository.save(UserMapper.mapToUser(libraryUserDto,lUser));

        ResponseDto rsp = new ResponseDto();
        rsp.setStatus(200);
        rsp.setDescription("Ok");
        rsp.setMessage("User updated successfully.");
        return rsp;
    }

    private void checkForExistUser(LibraryUserDto lud){
        LibraryUser inDBUserPhoneNumber = libraryUserRepository.findByPhoneNumber(lud.getPhoneNumber());
        LibraryUser inDBUserEmail = libraryUserRepository.findByEmail(lud.getEmail());

        if(inDBUserPhoneNumber != null && inDBUserEmail != null){
                throw new BadRequestException(
                        String.format("The the phone number %s and email address %s haven been used.",
                        lud.getPhoneNumber(),lud.getEmail())
                );
            }

            if(inDBUserPhoneNumber!= null && inDBUserPhoneNumber.getPhoneNumber().equals(lud.getPhoneNumber())){
                throw new BadRequestException(
                        String.format("The phone number %s has been used.", lud.getPhoneNumber())
                );

            }

            if(inDBUserEmail != null && inDBUserEmail.getEmail().equalsIgnoreCase(lud.getEmail())){
                throw new BadRequestException(
                        String.format("The email address %s has been used.", lud.getEmail())
                );
            }

    }
}
