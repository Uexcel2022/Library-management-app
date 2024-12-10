package com.uexcel.library.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Wisdom Spring Library REST APIs to obtain CSRF token",
        description = "Wisdom Spring Library REST APIs to obtain CSRF token"
)

@RestController
public class CsrfTokenController {
    @GetMapping("/api/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }
}
