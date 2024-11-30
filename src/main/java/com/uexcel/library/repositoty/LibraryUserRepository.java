package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, String> {
    LibraryUser findByEmailIgnoreCase(String email);
    LibraryUser findByPhoneNumber(String phoneNumber);
}
