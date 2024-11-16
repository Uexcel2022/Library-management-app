package com.uexcel.library.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter @Getter @ToString
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique=true)
    private String genreName;
    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();
}
