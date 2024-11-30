package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.exception.UnauthorizedException;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IPasswordChangeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.uexcel.library.service.IBookService.getTime;

@Service
@AllArgsConstructor
public class IPasswordChangeImpl implements IPasswordChangeService {
    private final Logger logger = LoggerFactory.getLogger(IPasswordChangeImpl.class);
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public ResponseDto passwordChangeAdmin(PasswordChangeDto passwordChangeDto,String email) {

        if(email == null || email.isEmpty()){
            throw new  BadRequestException("Password Change Dto is Null.");
        }

        Employee empAdmin = validateEmployee(passwordChangeDto);
        if(!empAdmin.getRole().equals("ADMIN")) {
            logger.debug("Security threat user: {}", empAdmin.getEmail());
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }

        Employee emp = employeeRepository.findByEmailIgnoreCase(email);
        if(emp == null) {
            throw new  BadRequestException("Employee not found given input data email: "+email);
        }

        if(!bCryptPasswordEncoder.matches(passwordChangeDto.getOldPassword(),emp.getPassword())){
            throw new  BadRequestException("Invalid password.");
        }

        emp.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));
        employeeRepository.save(emp);
        return getResponse();
    }

    @Override
    public ResponseDto passwordChangeEmployee(PasswordChangeDto passwordChangeDto) {

        Employee emp = validateEmployee(passwordChangeDto);

        if(!bCryptPasswordEncoder.matches(passwordChangeDto.getOldPassword(),emp.getPassword())){
            throw new  BadRequestException("Invalid password.");
        }
        emp.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));
        employeeRepository.save(emp);
        return getResponse();
    }


    private Employee validateEmployee(PasswordChangeDto pcd) {
        if(pcd == null){
            throw new  BadRequestException("Password Change Dto is Null");
        }
        if(!pcd.getConfirmNewPassword().equals(pcd.getNewPassword())){
            throw new  BadRequestException("Confirm password Not Match");
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee emp = employeeRepository.findByEmailIgnoreCase(name);

        if(emp == null){
            logger.debug("User not found in security context");
            throw new UnauthorizedException("You are not authorized to perform this operation");
        }
        return emp;
    }

    private ResponseDto getResponse() {
        ResponseDto rsp = new ResponseDto();
        rsp.setTimestamp(getTime());
        rsp.setStatus(200);
        rsp.setDescription("Ok");
        rsp.setMessage("Password updated successfully.");
        return rsp;
    }
}
