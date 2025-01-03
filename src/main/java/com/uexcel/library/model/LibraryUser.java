package com.uexcel.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class LibraryUser extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
