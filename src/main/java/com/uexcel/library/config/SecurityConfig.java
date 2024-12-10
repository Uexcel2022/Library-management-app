package com.uexcel.library.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .authorizeHttpRequests(
                        r->r.requestMatchers("/h2-console/**","/data-api","/api/csrf-token")
                                .permitAll()
                                .requestMatchers("/swagger-ui/**","/v3/api-doc*/**","/error").permitAll()
                                .requestMatchers("/api/fetch-all-books").permitAll()
                                .requestMatchers("/api/delete-book","/api/delete-user").hasAuthority("ADMIN")
                                .requestMatchers("/api/update-book","/api/update-user").hasAuthority("ADMIN")
                                .requestMatchers("api/add-employee","/api/fetch-employee").hasAuthority("ADMIN")
                                .requestMatchers("/api/delete-employee","/api/delete-rent").hasAuthority("ADMIN")
                                .requestMatchers("/api/pwd-chg-admin").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .headers(frame->frame.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();

    }

    @Bean
    public CompromisedPasswordChecker passwordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }



}
