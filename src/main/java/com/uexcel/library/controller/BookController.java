package com.uexcel.library.controller;

import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.BookRequestDto;
import com.uexcel.library.dto.ErrorResponseDto;
import com.uexcel.library.dto.ResponseDto;
import com.uexcel.library.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book CRUD REST APIs for Wisdom Spring Library",
        description = "REST APIs to manage Book Information in Wisdom Spring Library"
)

@RestController
@RequestMapping("api")
@AllArgsConstructor
@Validated
public class BookController {
    private final IBookService bookService;

    @Operation(
            summary = "REST API To Create Book Details",
            description = "REST API to create book details in Wisdom Spring Library",
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

    @PostMapping("/create-book")
    public ResponseEntity<ResponseDto> createBooks(@Valid @RequestBody BookRequestDto requestDto) {
       ResponseDto rs = bookService.createBook(requestDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }
    @Operation(
            summary = "REST API To Update Book Details",
            description = "REST API to update book details in Wisdom Spring Library",
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
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )

    @PutMapping("/update-book")
    public ResponseEntity<ResponseDto> updateBook(@Valid@ RequestBody BookDto bookDto) {
        ResponseDto resp = bookService.updateBook(bookDto);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @Operation(
            summary = "REST API To Delete Book Details",
            description = "REST API to delete book details using the [ title and author ] or the bookId in " +
                    "Wisdom Spring Library",
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
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )

    @DeleteMapping("/delete-book")
    public ResponseEntity<ResponseDto> deleteBook(@RequestParam(required = false) String title,
                                                       @RequestParam(required = false) String author,
                                                       @RequestParam(required = false) String bookId) {
        ResponseDto lb = bookService.deleteBook(title, author,bookId);
        return ResponseEntity.status(lb.getStatus()).body(lb);
    }

    @Operation(
            summary = "REST API To Fetch Book Details",
            description = "Wisdom Spring Library REST API to fetch book details and can filter by author and Genre.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = BookDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )

    @GetMapping("/fetch-all-books")
    public ResponseEntity<List<BookDto>> fetchAllBooks(
            @RequestParam(required = false) String author, @RequestParam(required = false) String genre) {
        List<BookDto> bookDtos = bookService.fetchAllBooks(author,genre);
        return ResponseEntity.ok().body(bookDtos);
    }
}
