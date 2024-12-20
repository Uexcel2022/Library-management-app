package com.uexcel.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter @Setter
public class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String password;
    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authority = new HashSet<>();
}
