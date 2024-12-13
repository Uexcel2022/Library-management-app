package com.uexcel.library.config;
import com.uexcel.library.exceptionhandling.CustomAccessDeniedHandler;
import com.uexcel.library.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.uexcel.library.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        return http
                .sessionManagement(smc->smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(2).maxSessionsPreventsLogin(true))
                .requiresChannel(rcc->rcc.anyRequest().requiresSecure()) //will accept only https request
                .csrf(csrf->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .authorizeHttpRequests(
                        r->r.requestMatchers("/h2-console/**","/data-api","/swagger-ui/**",
                                        "/v3/api-doc*/**").hasAuthority("ADMIN")
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/api/fetch-all-books","/api/csrf-token").permitAll()
                                .requestMatchers("/api/delete-book","/api/delete-user").hasAuthority("ADMIN")
                                .requestMatchers("/api/update-book","/api/update-user").hasAuthority("ADMIN")
                                .requestMatchers("api/add-employee","/api/fetch-employee").hasAuthority("ADMIN")
                                .requestMatchers("/api/delete-employee","/api/delete-rent").hasAuthority("ADMIN")
                                .requestMatchers("/api/pwd-chg-admin").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .headers(frame->frame.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))
                .exceptionHandling(ec->ec.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .formLogin(flc->flc.loginPage("/login").successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login?error=fail"))
                .logout(loc -> loc.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true).clearAuthentication(true)
                        .deleteCookies("JSESSIONID").logoutSuccessUrl("/login?logout=true"))
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
