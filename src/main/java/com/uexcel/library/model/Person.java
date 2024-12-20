package com.uexcel.library.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter @ToString
public class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
