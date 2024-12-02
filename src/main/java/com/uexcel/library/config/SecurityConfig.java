package com.uexcel.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
       return http
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       r->r.requestMatchers("/h2-console/**","/data-api")
                               .permitAll()
                               .requestMatchers("/swagger-ui/**","/v3/api-doc*/**").permitAll()
                               .requestMatchers("/api/fetch-all-books").permitAll()
                               .requestMatchers("/api/delete-book","api/delete-user").hasAuthority("ADMIN")
                               .requestMatchers("/api/update-book","/api/update-user").hasAuthority("ADMIN")
                               .requestMatchers("api/add-employee","/api/fetch-employee").hasAuthority("ADMIN")
                               .requestMatchers("/api/delete-employee","/api/delete-rent").hasAuthority("ADMIN")
                               .requestMatchers("api/pwd-chg-admin").hasAuthority("ADMIN")
                               .anyRequest()
                               .authenticated())
               .headers(frame->frame.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
               .httpBasic(withDefaults())
               .formLogin(withDefaults())
               .build();

    }

//    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                buildUser("admin","12348",new String[]{"USER","ADMIN"} ),
                buildUser("excel","12345",new String[]{"USER"} )
        );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails buildUser(String username, String password, String[] authorities) {
        return User.builder().passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

    Function<String, String> passwordEncoder = password -> bCryptPasswordEncoder().encode(password);

}
