package com.uexcel.library.service.impl;

import com.uexcel.library.config.LibraryConstants;
import com.uexcel.library.dto.AdminPasswordChangeDto;
import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.UnauthorizedException;
import com.uexcel.library.model.Employee;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IPasswordChangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class IPasswordChangeImpl implements IPasswordChangeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseDto passwordChangeAdmin(AdminPasswordChangeDto AdminPCD) {
        if(AdminPCD == null){
            throw new  BadRequestException("Password Change Dto is Null");
        }
        if(!AdminPCD.getConfirmPassword().equals(AdminPCD.getNewPassword())){
            throw new  BadRequestException("Confirm password Not Match");
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee empAdmin = employeeRepository.findByEmailIgnoreCase(name);

        if(empAdmin == null){
            log.debug("User in the in security context not found in the database.");
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }

        if(!empAdmin.getAuthority().stream().anyMatch(v->v.getName().equals(LibraryConstants.ADMIN))) {
            log.debug("Username: {} denied access to change password", empAdmin.getEmail());
            throw new UnauthorizedException("You are not authorized to perform this operation.");
        }

        Employee emp = employeeRepository.findByEmailIgnoreCase(AdminPCD.getEmail());
        if(emp == null) {
            throw new  BadRequestException("Employee not found given input data email: "+AdminPCD.getEmail());
        }

        emp.setPassword(passwordEncoder.encode(AdminPCD.getNewPassword()));
        employeeRepository.save(emp);
        return getResponse();
    }


    @Override
    public ResponseDto passwordChangeEmployee(PasswordChangeDto PCD) {

        if(PCD == null){
            throw new  BadRequestException("Password Change Dto is Null");
        }
        if(!PCD.getConfirmPassword().equals(PCD.getNewPassword())){
            throw new  BadRequestException("Confirm password Not Match");
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee emp = employeeRepository.findByEmailIgnoreCase(name);

        if(emp == null){
            log.debug("User in the in security context holder not found in the database.");
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }

        if(!passwordEncoder.matches(PCD.getOldPassword(),emp.getPassword())){
            throw new  BadRequestException("Invalid password.");
        }
        emp.setPassword(passwordEncoder.encode(PCD.getNewPassword()));
        employeeRepository.save(emp);
        return getResponse();
    }


    private ResponseDto getResponse() {
        ResponseDto rsp = new ResponseDto();
        rsp.setStatus(HttpStatus.OK.value());
        rsp.setDescription(HttpStatus.OK.getReasonPhrase());
        rsp.setMessage("Password updated successfully.");
        return rsp;
    }
}
