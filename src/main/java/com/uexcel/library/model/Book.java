package com.uexcel.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
public class Book extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String language;
    private LocalDate publishedDate;
    private String edition;
    private int quantity;
    private double price;
    private int borrowed;
    private int available;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
    private Genre genre;
}
