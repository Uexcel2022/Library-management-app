package com.uexcel.library.controller;

import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.service.IPasswordChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "REST APIs To Change Password")

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class ChangePasswordController {
    private final IPasswordChangeService passwordChangeService;

    @Operation(
            summary = "REST API To Update Password",
            description = "REST API to update password accessible to admin only in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )

    @PutMapping("/pwd-chg-admin")
    public ResponseEntity<ResponseDto> AdminChangePassword(
            @RequestBody PasswordChangeDto passwordChangeDto, @RequestParam String email){
        ResponseDto resp = passwordChangeService.passwordChangeAdmin(passwordChangeDto, email);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @Operation(
            summary = "REST API To Update Password",
            description = "REST API to update password in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )


            @PutMapping("/pwd-chg-emp")
    public ResponseEntity<ResponseDto> empChangePassword(
            @RequestBody PasswordChangeDto passwordChangeDto){
        ResponseDto resp = passwordChangeService.passwordChangeEmployee(passwordChangeDto);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
