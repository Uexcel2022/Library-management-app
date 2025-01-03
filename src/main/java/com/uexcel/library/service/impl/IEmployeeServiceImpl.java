package com.uexcel.library.service.impl;

import com.uexcel.library.admin.Admin;
import com.uexcel.library.config.LibraryConstants;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.model.Authority;
import com.uexcel.library.model.Employee;
import com.uexcel.library.repositoty.AuthorityRepository;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IEmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class IEmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker passwordChecker;
    private final AuthorityRepository authorityRepository;

    @Override
    public ResponseDto addEmployee(EmployeeDto empDto) {
        checkEmployeeNotNull(empDto);
        if(empDto.getPassword()==null || empDto.getPassword().isEmpty()){
            throw new BadRequestException("Password is required.");
        }

        if( passwordChecker.check(empDto.getPassword()).isCompromised()){
            throw new BadRequestException(
                    String.format("Password %s is compromised.",empDto.getPassword())
            );
        }

        checkEmployeeNotExist(empDto.getPhoneNumber(), empDto.getEmail());
        empDto.setPassword(passwordEncoder.encode(empDto.getPassword()));
        Employee employee = EmployeeMapper.mapToNewEmp(empDto,new Employee());
        Authority authority = new Authority();
        authority.setName(LibraryConstants.USER);
        authority.setEmployee(employee);
        Authority savedAuth = authorityRepository.save(authority);
        if(savedAuth.getId()>1) {
            ResponseDto rs = new ResponseDto();
            rs.setStatus(HttpStatus.CREATED.value());
            rs.setDescription(HttpStatus.CREATED.getReasonPhrase());
            rs.setMessage("Employee created Successfully.");
            return rs;
        }
        throw new BadRequestException("Failed to add employee.");
    }

    @Override
    public ResponseDto updateEmployee(UserDto userDto) {

        checkEmployeeNotNull(userDto);

        Employee employee = employeeRepository.findById(userDto.getId())
                .orElseThrow(() ->
                        new BadRequestException("Employee not found given input data employeeId: " + userDto.getId())
                );
        if(!userDto.getEmail().equalsIgnoreCase(employee.getEmail())) {
            checkEmployeeNotExist("0", userDto.getEmail());
        }

        if(!userDto.getPhoneNumber().equals(employee.getPhoneNumber())) {
            checkEmployeeNotExist(userDto.getPhoneNumber(),"0");
        }

        employeeRepository.save(EmployeeMapper.mapToUpdate(employee,userDto));
        ResponseDto rs = new ResponseDto();
        rs.setStatus(HttpStatus.OK.value());
        rs.setDescription(HttpStatus.OK.getReasonPhrase());
        rs.setMessage("Employee updated Successfully");
        return rs;
    }

    @Override
    public ResponseDto deleteEmployee(String employeeId) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new BadRequestException("Employee not found given input data employeeId: " + employeeId)
                );
        employeeRepository.delete(emp);
        ResponseDto rs = new ResponseDto();
        rs.setStatus(HttpStatus.OK.value());
        rs.setDescription(HttpStatus.OK.getReasonPhrase());
        rs.setMessage("Employee deleted Successfully");
        return rs;
    }

    @Override
    public List<Admin> getAllEmployee(String employeeId) {

        List<Employee> employeeList = employeeRepository.findAll();

        List<Admin> lAdmin = new ArrayList<>();

        if(employeeId!=null && !employeeId.isEmpty()) {
            employeeList.stream().filter(vr -> vr.getId().equals(employeeId)).toList()
                    .forEach(vr -> lAdmin.add(EmployeeMapper.mapToLibAdmin(new Admin(), vr)));
            if(lAdmin.isEmpty()) {
                throw new BadRequestException("Employee not found given input data employeeId: " + employeeId);
            }
            return lAdmin;
        }

         employeeList.forEach(vr -> lAdmin.add(EmployeeMapper.mapToLibAdmin(new Admin(), vr)));

        return lAdmin;
    }

    @Override
    public Admin getEmployee(String phoneNumber) {
        if(!phoneNumber.matches("0[7-9][01][0-9]{8}")){
            throw new BadRequestException("Please enter a valid phone number");
        }
        Employee emp = employeeRepository.findByPhoneNumber(phoneNumber);
        if(emp == null) {
            throw  new BadRequestException("Employee not found given input data phoneNumber: " + phoneNumber);
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = !emp.getAuthority().stream().anyMatch(authority->authority.getName().equals("ROLE_ADMIN"));

        if(!isAdmin && !emp.getEmail().equalsIgnoreCase(name)) {
            log.debug("Username {} denied access to other employee's details.", name );
            throw  new BadRequestException("You are not authorized to access this resource.");
        }

        return EmployeeMapper.mapToLibAdmin(new Admin(), emp);
    }


    private void checkEmployeeNotExist(String phoneNumber, String email) {

        Employee inDBEmpEmail = employeeRepository.findByEmailIgnoreCase(email);
        Employee inDBEmpPhoneNumber = employeeRepository.findByPhoneNumber(phoneNumber);

        if(inDBEmpEmail != null && inDBEmpPhoneNumber != null) {
            throw new BadRequestException(
                    String.format("The the email address %s and phone number %s haven been used.",
                            inDBEmpEmail.getEmail(), inDBEmpPhoneNumber.getPhoneNumber())
            );
        }

        if(inDBEmpEmail != null){
            throw new BadRequestException(
                    String.format("The email address %s has been used.", inDBEmpEmail.getEmail())
            );
        }

        if(inDBEmpPhoneNumber != null){
            throw new BadRequestException(
                    String.format("The phone number %s has been used.", inDBEmpPhoneNumber.getPhoneNumber())
            );

        }

    }

    private void checkEmployeeNotNull(UserDto userDto){
        if(userDto==null){
            throw new BadRequestException("RequestBody EmployeeDto is Null.");
        }
    }
}
