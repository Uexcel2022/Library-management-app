package com.uexcel.library.config;

import com.uexcel.library.exceptionhandling.CustomAccessDeniedHandler;
import com.uexcel.library.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.uexcel.library.filter.CsrfCookieFilter;
import com.uexcel.library.filter.CustomAuthenticationLoggingFilter;
import com.uexcel.library.filter.CustomRequestValidationFilter;
import com.uexcel.library.handler.CustomAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Profile("prod")
@AllArgsConstructor
public class SecurityConfig {
    private  final  CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .securityContext(contextConfig->contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .sessionManagement(smc->smc.invalidSessionUrl("/login?error=invalidSession")
                        .maximumSessions(2).maxSessionsPreventsLogin(true))
                .requiresChannel(rcc->rcc.anyRequest().requiresSecure()) //will accept only https request

                .addFilterBefore(new CustomRequestValidationFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new CustomAuthenticationLoggingFilter(),BasicAuthenticationFilter.class)

                .csrf(csrf->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .authorizeHttpRequests(
                        r->r.requestMatchers("/h2-console/**","/data-api").hasRole("ADMIN")
                                .requestMatchers("/api/csrf-token").permitAll()
                                .requestMatchers("/api/fetch-all-books","/login/**","/error").permitAll()
                                .requestMatchers("/swagger-ui/**","/v3/api-doc*/**").hasRole("ADMIN")
                                .requestMatchers("/api/delete-book","/api/delete-user").hasRole("ADMIN")
                                .requestMatchers("/api/update-book","/api/update-user").hasRole("ADMIN")
                                .requestMatchers("api/add-employee","/api/fetch-employee").hasRole("ADMIN")
                                .requestMatchers("/api/delete-employee","/api/delete-rent").hasRole("ADMIN")
                                .requestMatchers("/api/pwd-chg-admin").hasRole("ADMIN")
                                .anyRequest().authenticated())
//                .headers(frame->frame.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
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
