package com.uexcel.library.Entity;

import jakarta.persistence.*;

@Entity
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;
    private String paid;
    private String returned;
    @ManyToOne
    private Book books;
    @ManyToOne()
    private User users;

}
