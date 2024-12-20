package com.uexcel.library.repositoty;

import com.uexcel.library.model.Book;
import com.uexcel.library.model.LibraryUser;
import com.uexcel.library.model.BookRent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRentRepository extends JpaRepository<BookRent,String> {

    BookRent findByLibraryUserIdAndBookIdAndReturned(String userId, String bookId, boolean returned);

    @Modifying
    @Transactional
    void deleteByBook(Book bk);

    List<BookRent> findByBook(Book book);
    List<BookRent> findByLibraryUser(LibraryUser user);

     @Modifying
     @Transactional
    void deleteByLibraryUser(LibraryUser user);

    List<BookRent> findByBookId(String id);
}
