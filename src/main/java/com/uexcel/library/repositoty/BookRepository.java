package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Book findByTitleAndAuthor(String title, String author);
}
