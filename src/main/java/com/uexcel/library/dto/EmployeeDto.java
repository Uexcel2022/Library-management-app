package com.uexcel.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EmployeeDto extends LibraryUserDto{
    private String password;
}
