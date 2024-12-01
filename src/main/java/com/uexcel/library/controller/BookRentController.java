package com.uexcel.library.controller;

import com.uexcel.library.dto.*;
import com.uexcel.library.service.IRentBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@Tag(name = "Book Rent CRUD REST APIs For Wisdom Spring Library",
        description = "CRUD RESP APIs in Wisdom Spring Library for managing book lending."
)
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRentController {
    private final IRentBookService rentBookService;

    @Operation(
            summary = "REST API To Create Book Rent Details",
            description = "REST API to create book rent details in Wisdom Spring Library",
            responses = {
            @ApiResponse(
                    responseCode = "201", description = "Ok",
                    content = @Content(schema = @Schema(implementation = BookRentDto.class))
            ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )
    @PostMapping("/rent")
    public ResponseEntity<BookRentDto> rentBook(@Valid @RequestBody BookRentRequestDto bookRentRequestDto) {
        BookRentDto lb = rentBookService.createBookRentDetails(bookRentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lb);
    }
    @Operation(
            summary = "REST API To Update Returned Book Rent Details",
            description = "REST API to update only the returned book status using [ title, author and phoneNumber ]" +
                    " or the rentId in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
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
    @PutMapping("/return")
    public ResponseEntity<ResponseDto> returnBook(
            @Valid @RequestBody(required = false) UserBookDto userBookDto,
            @RequestParam(required = false) String rentId) {
        ResponseDto rsp = rentBookService.returnBook(userBookDto,rentId);
        return ResponseEntity.status(rsp.getStatus()).body(rsp);
    }

    @Operation(
            summary = "REST API To Delete A Book Rent Details",
            description = "REST API to delete all the rent details of a book in Wisdom Spring Library",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
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

    @DeleteMapping("/delete-rent")
    public ResponseEntity<ResponseDto> deleteRent(@Valid@RequestBody UserBookDto userBookDto) {
        ResponseDto lb = rentBookService.deleteRentBook(userBookDto,"Book rent details");
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @Operation(
            summary = "REST API To Fetch Rent Details",
            description = "Wisdom Spring Library REST API to fetch rented books and can filter by bookId, " +
                    "phoneNumber and returned status and delete specific rent details using rentId.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = BookRentDto.class))
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

    @GetMapping("/fetch-rent")
    public ResponseEntity<List<BookRentDto>> fetchRent(@RequestParam(required = false) String bookId,
                                                       @RequestParam(required = false) String phoneNumber,
                                                       @RequestParam(required = false) boolean returned,
                                                       @RequestParam(required = false) String rentId) {
        List<BookRentDto> brd = rentBookService.fetchRentBook(bookId,phoneNumber, returned,rentId);
        return ResponseEntity.ok().body(brd);
    }



}
