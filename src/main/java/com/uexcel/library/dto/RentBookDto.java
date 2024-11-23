package com.uexcel.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RentBookDto {
    private boolean paid;
    private boolean returned;
    private String phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;
    private String bootTile;
    private String author;
    private int quantity;
    private LocalDate date;
    private LocalDate dueDate;
    private double amount;
}
