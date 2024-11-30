package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.admin.Admin;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.uexcel.library.service.IBookService.getTime;

@Service
@AllArgsConstructor
public class IEmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseDto addEmployee(EmployeeDto empDto) {
        checkEmployeeNotNull(empDto);
        if(empDto.getPassword()==null || empDto.getPassword().isEmpty()){
            throw new BadRequestException("Password is required.");
        }
        checkEmployeeNotExist(empDto.getPhoneNumber(), empDto.getEmail());
        empDto.setPassword(bCryptPasswordEncoder.encode(empDto.getPassword()));
        employeeRepository.save(EmployeeMapper.mapToNewEmp(empDto,new Employee()));
        ResponseDto rs = new ResponseDto();
        rs.setTimestamp(getTime());
        rs.setStatus(201);
        rs.setDescription("Created");
        rs.setMessage("Employee created Successfully");
        return rs;
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
        rs.setTimestamp(getTime());
        rs.setStatus(200);
        rs.setDescription("OK");
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
        rs.setTimestamp(getTime());
        rs.setStatus(200);
        rs.setDescription("Ok");
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
        if(emp.getRole().equalsIgnoreCase("ADMIN") || !emp.getEmail().equalsIgnoreCase(name)) {
            throw  new BadRequestException("You are not authorized to access this employee.");
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
