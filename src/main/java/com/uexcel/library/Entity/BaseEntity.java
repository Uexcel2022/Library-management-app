package com.uexcel.library.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {
    @JsonIgnore
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    @Column(insertable = false)
    private String modifiedBy;
    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;
    @JsonIgnore
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate modifiedAt;
}
