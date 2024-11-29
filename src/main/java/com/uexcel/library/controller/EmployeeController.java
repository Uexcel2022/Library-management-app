package com.uexcel.library.controller;

import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.service.IEmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "CRUD REST APIs For Creating And Managing Employee Details",
description = "CRUD REST API For Creating And Managing Employee Details In Wisdom Spring Library")
@RestController
@RequestMapping("/api")
@Validated
public class EmployeeController {
    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add-employee")
    public ResponseEntity<ResponseDto> addEmployee( @Valid @RequestBody EmployeeDto employeeDto) {
       ResponseDto rs = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }
}
