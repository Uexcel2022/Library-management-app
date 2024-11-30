package com.uexcel.library.mapper;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.admin.Admin;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.UserDto;

public class EmployeeMapper {
    /**
     * Used to create new employee.
     */
    public static Employee mapToNewEmp(EmployeeDto employeeDto, Employee employee) {
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setRole("EMPLOYEE");
        return employee;
    }

    public static Employee mapToUpdate(Employee employee, UserDto userDto) {
        employee.setId(userDto.getId());
        employee.setFirstName(userDto.getFirstName());
        employee.setLastName(userDto.getLastName());
        employee.setPhoneNumber(userDto.getPhoneNumber());
        employee.setEmail(userDto.getEmail());
        return employee;
    }

    /**
     * Used to create a default account for admin.
     */
    public static Employee mapToNewEmp(Admin lAdmin, Employee emp) {
        emp.setLastName(lAdmin.getLastName());
        emp.setFirstName(lAdmin.getFirstName());
        emp.setPhoneNumber(lAdmin.getPhoneNumber());
        emp.setEmail(lAdmin.getEmail());
        emp.setPassword(lAdmin.getPassword());
        emp.setRole(lAdmin.getRole());
        emp.setPassword(lAdmin.getPassword());
        return emp;
    }
    /**
     * Used to return employee details.
     */
    public static Admin mapToLibAdmin(Admin lAdmin, Employee emp) {
        lAdmin.setId(emp.getId());
        lAdmin.setFirstName(emp.getFirstName());
        lAdmin.setLastName(emp.getLastName());
        lAdmin.setPhoneNumber(emp.getPhoneNumber());
        lAdmin.setEmail(emp.getEmail());
        lAdmin.setRole(emp.getRole());
        return lAdmin;
    }
}
