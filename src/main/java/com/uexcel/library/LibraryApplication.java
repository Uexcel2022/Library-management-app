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
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(Admin.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@AllArgsConstructor
public class LibraryApplication {
	private final EmployeeRepository employeeRepository;
	private final CompromisedPasswordChecker cPC;
	private final Admin lAdmin;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
	@PostConstruct
	public void  addAdMin(){
		if(cPC.check(lAdmin.getPassword()).isCompromised()){
			throw new CompromisedPasswordException(
					String.format("Password '%s' is compromised.", lAdmin.getPassword())
			);
		}
		lAdmin.setPassword(passwordEncoder.encode(lAdmin.getPassword()));
		employeeRepository.save(EmployeeMapper.mapToNewEmp(lAdmin,new Employee()));
	}

}
