package com.uexcel.library.repositoty;

import com.uexcel.library.Entity.LibraryUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<LibraryUser, String> {
    LibraryUser findByPhoneNumberOrEmail(String phoneNumber, String email);

    LibraryUser findByPhoneNumber(String phoneNumber);
}
