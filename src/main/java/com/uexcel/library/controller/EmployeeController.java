package com.uexcel.library.controller;

import com.uexcel.library.admin.Admin;
import com.uexcel.library.dto.EmployeeDto;
import com.uexcel.library.dto.ErrorResponseDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee CRUD REST APIs",
description = "CRUD REST API For Creating And Managing Employee Details In Wisdom Spring Library")
@RestController
@RequestMapping("/api")
@Validated
public class EmployeeController {
    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "REST API To Create Employee Details",
            description = "REST API to create employee details in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )


    @PostMapping("/add-employee")
    public ResponseEntity<ResponseDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
       ResponseDto rs = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }

    @Operation(
            summary = "REST API To Update employee Details",
            description = "REST API to update employee details in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )


    @PutMapping("/update-employee")
    public ResponseEntity<ResponseDto> updateEmployee(@Valid @RequestBody UserDto userDto) {
        ResponseDto rs = employeeService.updateEmployee(userDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }

    @Operation(
            summary = "REST API To Fetch All Employee Details",
            description = "REST API to fetch employees details reduce by ID in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = Admin.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )


    @GetMapping("/fetch-employee")
    public ResponseEntity<List<Admin>> fetchEmployee(
            @RequestParam(required = false) String employeeId) {
        List<Admin> lAdmin = employeeService.getAllEmployee(employeeId);
        return ResponseEntity.ok().body(lAdmin);
    }


    @Operation(
            summary = "REST API To Fetch Employee Details",
            description = "REST API to fetch employee details by phone number in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = Admin.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )


    @GetMapping("/fetch-employee-phone")
    public ResponseEntity<Admin> fetchEmployeeById(@RequestParam String phoneNumber) {
        Admin lAdmin = employeeService.getEmployee(phoneNumber);
        return ResponseEntity.ok().body(lAdmin);
    }

    @Operation(
            summary = "REST API To Delete Employee Details",
            description = "REST API to delete employee details by ID in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )


    @GetMapping("/delete-employee")
    public ResponseEntity<ResponseDto> deleteEmployee(@RequestParam String employeeId) {
        ResponseDto resp = employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
