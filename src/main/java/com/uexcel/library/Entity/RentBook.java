package com.uexcel.library.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter @Getter @ToString
public class RentBook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private boolean paid;
    private boolean returned;
    private int quantity;
    private LocalDate dueDate;
    private LocalDate date;
    private double amount;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Book book;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private LibraryUser libraryUser;

}
