package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.Book;
import com.uexcel.library.Entity.LibraryUser;
import com.uexcel.library.Entity.RentBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RentBookRepository extends JpaRepository<RentBook,String> {
    RentBook findByLibraryUserAndBookAndReturned(LibraryUser user, Book book, boolean returned);
    @Modifying
    @Transactional
    void deleteByBook(Book bk);

    List<RentBook> findByBook(Book book);
    List<RentBook> findByLibraryUser(LibraryUser user);

     @Modifying
     @Transactional
    void deleteByLibraryUser(LibraryUser user);
}
