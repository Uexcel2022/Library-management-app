package com.uexcel.library.mapper;

import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.UserDto;

public class UserMapper {
    public static LibraryUser mapToUser(UserDto userDto, LibraryUser libraryUser) {
        libraryUser.setFirstName(userDto.getFirstName());
        libraryUser.setLastName(userDto.getLastName());
        libraryUser.setPhoneNumber(userDto.getPhoneNumber());
        libraryUser.setEmail(userDto.getEmail());
        return libraryUser;
    }

    public static UserDto mapToUserDto(LibraryUser libraryUser, UserDto userDto) {
        userDto.setId(libraryUser.getId());
        userDto.setFirstName(libraryUser.getFirstName());
        userDto.setLastName(libraryUser.getLastName());
        userDto.setPhoneNumber(libraryUser.getPhoneNumber());
        userDto.setEmail(libraryUser.getEmail());
        return userDto;
    }
}
