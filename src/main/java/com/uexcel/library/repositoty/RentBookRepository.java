package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.RentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RentBookRepository extends JpaRepository<RentBook,String> {
    RentBook findByLibraryUserAndBookAndReturned(LibraryUser user, Book book, boolean returned);
}
