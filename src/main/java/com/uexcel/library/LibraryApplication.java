package com.uexcel.library;


import com.uexcel.library.admin.Admin;
import com.uexcel.library.config.LibraryConstants;
import com.uexcel.library.exception.BadRequestException;
import com.uexcel.library.mapper.EmployeeMapper;
import com.uexcel.library.model.Authority;
import com.uexcel.library.model.Employee;
import com.uexcel.library.repositoty.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(Admin.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@AllArgsConstructor
public class LibraryApplication {
	private final CompromisedPasswordChecker cPC;
	private final AuthorityRepository authorityRepository;
	private final Admin lAdmin;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@PostConstruct
	@Transactional
	public void  addAdMin(){
		if(lAdmin.getPassword().length() < 8 || lAdmin.getPassword().length()> 16){
			throw new BadRequestException("Password must be 8 - 16 characters.");
		}

		if(cPC.check(lAdmin.getPassword()).isCompromised()){
			throw new CompromisedPasswordException(
					String.format("Password '%s' is compromised.", lAdmin.getPassword())
			);
		}

		lAdmin.setPassword(passwordEncoder.encode(lAdmin.getPassword()));
		Employee emp = EmployeeMapper.mapToNewEmp(lAdmin,new Employee());

		List<Authority> authorities = new ArrayList<>();

		Authority authority1 = new Authority();
		authority1.setName(LibraryConstants.ADMIN);
		authority1.setEmployee(emp);
		authorities.add(authority1);

		Authority authority2 = new Authority();
		authority2.setName(LibraryConstants.USER);
		authority2.setEmployee(emp);
		authorities.add(authority2);

		List<Authority> authorityList = authorityRepository.saveAll(authorities);
		for(Authority authority : authorityList){
			if(authority.getId() < 1){
				throw new BadRequestException("Error Encountered While Creating Admin.");
			}
		}
	}
}
