package com.uexcel.library.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String language;
    private String publishedDate;
    private String edition;
    private int quantity;
    private double price;
    private int borrowed;
    private int available;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Genre genre;
}
