package com.uexcel.library.mapper;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.dto.EmployeeDto;

public class EmployeeMapper {
    public static Employee mapToEmp(EmployeeDto employeeDto, Employee employee) {
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setRole("USER");
        return employee;
    }

    public static EmployeeDto mapToEmpDto(Employee employee, EmployeeDto employeeDto) {
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPassword(employee.getPassword());
        return employeeDto;
    }
}
