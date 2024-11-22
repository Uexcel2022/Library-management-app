package com.uexcel.library.mapper;

import com.uexcel.library.Entity.User;
import com.uexcel.library.dto.UserDto;

public class UserMapper {
    public static User mapToUser(UserDto userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto mapToUserDto(User user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
