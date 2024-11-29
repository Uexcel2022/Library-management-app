package com.uexcel.library.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;


@Entity
@Setter @Getter @ToString
@Validated
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique=true)
    @Pattern(regexp = "[A-Za-z]+",message = "Genre should contain only alphabets.")
    private String genreName;
}
