package com.uexcel.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wisdom Spring Library REST APIs to obtain CSRF token",
        description = "Wisdom Spring Library REST APIs to obtain CSRF token"
)

@RestController
public class CsrfTokenController {
    @GetMapping("/api/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request, HttpServletResponse response){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        response.setHeader("XSRF-TOKEN", csrfToken.getToken());
        return csrfToken;
    }
}
