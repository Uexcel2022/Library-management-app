package com.uexcel.library;


import com.uexcel.library.Entity.Employee;
import com.uexcel.library.admin.Admin;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.repositoty.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(Admin.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@AllArgsConstructor
public class LibraryApplication {
	private final EmployeeRepository employeeRepository;
	private final Admin lAdmin;
	private final BCryptPasswordEncoder bCrypt;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
	@PostConstruct
	public void  addAdMin(){
		lAdmin.setPassword(bCrypt.encode(lAdmin.getPassword()));
		employeeRepository.save(EmployeeMapper
				.mapToNewEmp(lAdmin,new Employee()));
	}

}
