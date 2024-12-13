package com.uexcel.library.controller;

import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.dto.ErrorResponseDto;
import com.uexcel.library.service.ILibraryUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User CRUD REST APIs for Wisdom Spring Library",
description = "Wisdom Spring Library REST APIs for creating and managing the information of the Users")

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryUserController {
    private final ILibraryUserService userService;

    @Operation(
            summary = "REST API To Create User Details",
            description = "REST API to create user details in Wisdom Spring Library",
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

    @PostMapping("/create-user")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto){
        ResponseDto rs = userService.createUser(userDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }

    @Operation(
            summary = "REST API To Fetch User Details",
            description = "REST API to fetch a user details in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
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

    @GetMapping("/fetch-user")
    public ResponseEntity<UserDto> fetchUser(@RequestParam String phoneNumber){
        UserDto lib = userService.fetchUser(phoneNumber);
        return ResponseEntity.ok().body(lib);
    }

    @Operation(
            summary = "REST API To Delete User Details",
            description = "REST API to a delete user details in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
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

    @DeleteMapping("/delete-user")
    public ResponseEntity<ResponseDto> deleteUser(@RequestParam String phoneNumber){
        ResponseDto rsp = userService.deleteUser(phoneNumber);
        return ResponseEntity.status(rsp.getStatus()).body(rsp);
    }

    @Operation(
            summary = "REST API To Update User Details",
            description = "REST API to update user details in Wisdom Spring Library",
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

    @PutMapping("/update-user")
    public ResponseEntity<ResponseDto> updateUser(@Valid @RequestBody UserDto userDto){
        ResponseDto rsp = userService.updateUser(userDto);
        return ResponseEntity.status(rsp.getStatus()).body(rsp);
    }
}
