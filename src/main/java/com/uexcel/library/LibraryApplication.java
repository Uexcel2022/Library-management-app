package com.uexcel.library;


import com.uexcel.library.Entity.Employee;
import com.uexcel.library.admin.LibraryAdmin;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.repositoty.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(LibraryAdmin.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@AllArgsConstructor
public class LibraryApplication {
	private final EmployeeRepository employeeRepository;
	private final LibraryAdmin lAdmin;
	private final BCryptPasswordEncoder bCrypt;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
	@PostConstruct
	public void  addAdMin(){
		System.out.println(lAdmin);
		lAdmin.setPassword(bCrypt.encode(lAdmin.getPassword()));
		employeeRepository.save(EmployeeMapper
				.mapToEmp(lAdmin,new Employee()));
	}

}
