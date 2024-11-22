package com.uexcel.library.mapper;


import com.uexcel.library.Entity.RentBook;
import com.uexcel.library.dto.RentBookDto;

import java.time.LocalDate;

public class RentBookMapper {
    public static RentBookDto mapToRentBookDto(RentBook rentbook, RentBookDto rentBookDto) {
        rentBookDto.setBootTile(rentBookDto.getBootTile());
        rentBookDto.setDueDate(rentbook.getDueDate());
        rentBookDto.setDate(rentbook.getDate());
        rentBookDto.setPaid(rentbook.isPaid());
        rentBookDto.setReturned(rentbook.isReturned());
        return rentBookDto;
    }

    public static RentBook mapToRentBook(RentBookDto rentBookDto, RentBook rentBook) {
        rentBook.setDueDate(LocalDate.now().plusDays(90));
        rentBook.setDate(LocalDate.now());
        rentBook.setPaid(rentBookDto.isPaid());
        rentBook.setReturned(rentBookDto.isReturned());
        rentBook.setQuantity(rentBookDto.getQuantity());
        rentBook.setAmount(rentBookDto.getAmount());
        return rentBook;

    }
}
