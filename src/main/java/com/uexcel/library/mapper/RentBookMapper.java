package com.uexcel.library.mapper;


import com.uexcel.library.Entity.BookRent;
import com.uexcel.library.dto.RentBookDto;

import java.time.LocalDate;

public class RentBookMapper {
    public static RentBookDto mapToRentBookDto(BookRent rentbook, RentBookDto rentBookDto) {
        rentBookDto.setDueDate(rentbook.getDueDate());
        rentBookDto.setDate(rentbook.getDate());
        rentBookDto.setPaid(rentbook.isPaid());
        rentBookDto.setReturned(rentbook.isReturned());
        rentBookDto.setAmount(rentbook.getAmount());
        return rentBookDto;
    }

    public static BookRent mapToRentBook(RentBookDto rentBookDto, BookRent bookRent) {
        bookRent.setDueDate(LocalDate.now().plusDays(90));
        bookRent.setDate(LocalDate.now());
        bookRent.setPaid(rentBookDto.isPaid());
        bookRent.setReturned(rentBookDto.isReturned());
        bookRent.setQuantity(rentBookDto.getQuantity());
        bookRent.setAmount(rentBookDto.getAmount());
        return bookRent;

    }
}
