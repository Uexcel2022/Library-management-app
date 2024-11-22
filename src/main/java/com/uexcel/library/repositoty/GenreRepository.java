package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    Optional<Genre> findByGenreName(String genreName);
}
