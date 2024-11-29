package com.uexcel.library.service.impl;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.repositoty.EmployeeRepository;
import com.uexcel.library.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import static com.uexcel.library.service.IBookService.getTime;

@Service
@AllArgsConstructor
public class IEmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseDto addEmployee(EmployeeDto employeeDto) {
        if(employeeDto==null){
            throw new BadRequestException("Input is Null");
        }
        employeeDto.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));
        employeeRepository.save(EmployeeMapper.mapToEmp(employeeDto,new Employee()));
        ResponseDto rs = new ResponseDto();
        rs.setTimestamp(getTime());
        rs.setStatus(201);
        rs.setDescription("Created");
        rs.setMessage("Employee created Successfully");
        return rs;
    }
}
