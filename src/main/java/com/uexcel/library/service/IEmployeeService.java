package com.uexcel.library.service;

import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;

public interface IEmployeeService {
    ResponseDto addEmployee(EmployeeDto employeeDto);
}
