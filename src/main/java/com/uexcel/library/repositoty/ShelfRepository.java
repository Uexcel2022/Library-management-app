package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf,String> {
}
