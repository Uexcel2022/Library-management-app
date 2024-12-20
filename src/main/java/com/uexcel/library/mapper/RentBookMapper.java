package com.uexcel.library.mapper;


import com.uexcel.library.model.BookRent;
import com.uexcel.library.dto.BookDto;
import com.uexcel.library.dto.BookRentRequestDto;
import com.uexcel.library.dto.UserDto;
import com.uexcel.library.dto.BookRentDto;

import java.time.LocalDate;

public class RentBookMapper {
    public static BookRentDto mapToRentBookDto(BookRent bookRent, BookRentDto bookRentDto) {
        bookRentDto.setId(bookRent.getId());
        bookRentDto.setDueDate(bookRent.getDueDate());
        bookRentDto.setDate(bookRent.getDate());
        bookRentDto.setPaid(bookRent.isPaid());
        bookRentDto.setReturned(bookRent.isReturned());
        bookRentDto.setQuantity(bookRent.getQuantity());
        bookRentDto.setAmount(bookRent.getAmount());
        bookRentDto.setLibraryUser(UserMapper.mapToUserDto(bookRent.getLibraryUser(),new UserDto()));
        bookRentDto.setBook(BookMapper.mapToBookDto(bookRent.getBook(),new BookDto()));
        return bookRentDto;
    }

    public static BookRent mapToRentBook(BookRentRequestDto bookRentRequestDto, BookRent bookRent) {
        bookRent.setDueDate(LocalDate.now().plusDays(90));
        bookRent.setDate(LocalDate.now());
        bookRent.setQuantity(bookRentRequestDto.getQuantity());
        return bookRent;

    }
}
