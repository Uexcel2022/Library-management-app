package com.uexcel.library.config;

import com.uexcel.library.model.Employee;
import com.uexcel.library.repositoty.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {
   private final EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Employee emp = employeeRepository.findByEmailIgnoreCase(username);
      if (emp == null) {
          throw new UsernameNotFoundException(
                  String.format("User not found with username: ",username)
          );
      }
      List<GrantedAuthority> authorities = emp.getAuthority().stream()
                      .map(authority-> new SimpleGrantedAuthority(authority.getName()))
              .collect(Collectors.toUnmodifiableList());
      return new User(emp.getEmail(),emp.getPassword(),authorities);
    }
}
