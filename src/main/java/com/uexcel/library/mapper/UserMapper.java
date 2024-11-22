package com.uexcel.library.mapper;

import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.dto.LibraryUserDto;

public class UserMapper {
    public static LibraryUser mapToUser(LibraryUserDto libraryUserDto, LibraryUser libraryUser) {
        libraryUser.setFirstName(libraryUserDto.getFirstName());
        libraryUser.setLastName(libraryUserDto.getLastName());
        libraryUser.setPhoneNumber(libraryUserDto.getPhoneNumber());
        libraryUser.setEmail(libraryUserDto.getEmail());
        return libraryUser;
    }

    public static LibraryUserDto mapToUserDto(LibraryUser libraryUser, LibraryUserDto libraryUserDto) {
        libraryUserDto.setId(libraryUser.getId());
        libraryUserDto.setFirstName(libraryUser.getFirstName());
        libraryUserDto.setLastName(libraryUser.getLastName());
        libraryUserDto.setPhoneNumber(libraryUser.getPhoneNumber());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }
}
