package com.uexcel.library.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Setter @Getter @ToString
public class BookRent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private boolean paid;
    private boolean returned;
    private int quantity;
    private LocalDate dueDate;
    private LocalDate date;
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Book book;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private LibraryUser libraryUser;

}
