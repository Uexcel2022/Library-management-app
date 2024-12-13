package com.uexcel.library.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

import static com.uexcel.library.service.IBookService.getTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("wisdom-spring-library", "Authentication Failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String msg = (authException != null && authException.getMessage()!=null)?
                authException.getMessage() : "Authentication Failed";
        String jsonResponse =
                String.format("{\"timestamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\",\"message\":\"%s\"," +
                        "\"apiPath\":\"%s\"}", getTime(), HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),msg,request.getRequestURI());
        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(jsonResponse);
    }

}
