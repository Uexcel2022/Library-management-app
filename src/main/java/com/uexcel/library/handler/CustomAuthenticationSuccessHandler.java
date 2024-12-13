package com.uexcel.library.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(v -> v.getAuthority().equals("ADMIN"));
         CsrfToken csrfToken =   (CsrfToken)request.getAttribute("_csrf");

        if (isAdmin) {
            response.setHeader("X-XSRF-TOKEN",csrfToken.getToken());
            response.sendRedirect("/swagger-ui.html");
        } else {
            response.sendRedirect("/login?error=access_denied");
        }
    }
}