package com.uexcel.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
