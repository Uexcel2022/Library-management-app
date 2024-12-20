package com.uexcel.library.repositoty;

import com.uexcel.library.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByEmailIgnoreCase(String username);

    Employee findByPhoneNumber(String username);

}
