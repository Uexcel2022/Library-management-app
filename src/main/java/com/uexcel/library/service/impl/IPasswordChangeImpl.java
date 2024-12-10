package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.dto.AdminPasswordChangeDto;
import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.UnauthorizedException;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IPasswordChangeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IPasswordChangeImpl implements IPasswordChangeService {
    private final Logger logger = LoggerFactory.getLogger(IPasswordChangeImpl.class);
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
            logger.debug("User in the in security context holder not found in the database.");
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }

        if(!empAdmin.getRole().equals("ADMIN")) {
            logger.debug("Security threat user: {}", empAdmin.getEmail());
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
            logger.debug("User in the in security context holder not found in the database.");
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
