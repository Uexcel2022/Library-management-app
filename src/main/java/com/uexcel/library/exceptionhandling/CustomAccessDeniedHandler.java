package com.uexcel.library.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.uexcel.library.service.IBookService.getTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("wisdom-spring-library", "Authentication Failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String msg = (accessDeniedException != null && accessDeniedException.getMessage()!=null)?
                accessDeniedException.getMessage() : "Authentication Failed";
        String jsonResponse =
                String.format("{\"timestamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\",\"message\":\"%s\"," +
                                "\"apiPath\":\"%s\"}", getTime(), HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),msg,request.getRequestURI());
        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(jsonResponse);
    }
}
