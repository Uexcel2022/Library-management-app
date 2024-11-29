package com.uexcel.library.authentication;

import com.uexcel.library.Entity.Employee;
import com.uexcel.library.repositoty.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if(password == null || username == null) {
            throw new BadCredentialsException("Bad credentials!");
        }

        Employee emp;
        if(username.contains("@")) {
            emp = employeeRepository.findByEmail(username);
        } else{
            emp = employeeRepository.findByPhoneNumber(username);
        }

        if(null != emp && !emp.getId().equals(null) && passwordEncoder.matches(password, emp.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, null,
                    List.of(new GrantedAuthority[]{new SimpleGrantedAuthority(emp.getRole())}));
        }

        throw  new BadCredentialsException("Bad credentials!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
