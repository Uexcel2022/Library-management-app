package com.uexcel.library.service;

import com.uexcel.library.model.LibraryUser;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.exception.ResourceNotFoundException;

public interface ILibraryUserService {
    /**
     * @param userDto - contains user information
     * @return status and message
     */
    ResponseDto createUser(UserDto userDto);


    UserDto fetchUser(String phoneNumber);

    /**
     * @param phoneNumber - will hold user phone number
     * @return - response status and message
     */
    ResponseDto deleteUser(String phoneNumber);

    ResponseDto updateUser(UserDto userDto);

    static void validateUserNotNull(LibraryUser user,String phoneNumber) {
        if(user == null) {
            throw new ResourceNotFoundException(
                    String.format(
                            "User not found for the given input data phoneNumber: %s", phoneNumber
                    )
            );
        }
    }

}
