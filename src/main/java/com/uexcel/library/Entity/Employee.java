package com.uexcel.library.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@Entity
public class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String password;
    private String role;
}
