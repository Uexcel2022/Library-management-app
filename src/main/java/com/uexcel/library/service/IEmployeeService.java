package com.uexcel.library.service;

import com.uexcel.library.admin.Admin;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;

import java.util.List;

public interface IEmployeeService {
    ResponseDto addEmployee(EmployeeDto employeeDto);
    ResponseDto updateEmployee(UserDto userDto);
    ResponseDto deleteEmployee(String employeeId);
    List<Admin> getAllEmployee(String employeeId);
    Admin getEmployee(String phoneNumber);

}
