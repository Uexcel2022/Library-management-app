package com.uexcel.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class CustomRequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String X_XSRF_TOKEN  = request.getHeader("X-XSRF-TOKEN");

        List<String> avoid = new ArrayList<>();
        avoid.add("/error");
        avoid.add("/favicon.ico");
        avoid.add("/login");
        avoid.add("/logout");
        avoid.add("/v3/api-docs");

        if(header != null){
            String trimmedHeader = header.trim();
            if(!StringUtils.startsWith(trimmedHeader,"Basic ")){
                throw new BadCredentialsException("Invalid Authorization Token");
            }
            byte [] base64Token = trimmedHeader.substring(6).getBytes();
            byte[] decode;
            try {
                 decode = Base64.getDecoder().decode(base64Token);
                 String token = new String(decode);
                 int delim = token.indexOf(":");
                 String username = token.substring(0,delim);
                 String password = token.substring(delim+1);
                 //for demo purpose
                if(username.contains("xxx") || password.length() < 8 ){

                    if(avoid.contains(request.getRequestURI())){
                        response.sendRedirect("/login?error=true");
                    }
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"message\":\"Invalid username or password\"}");
                    return;
                }

            } catch (Exception exception){
                throw new BadCredentialsException("Fail to decode authentication token");
            }

        } else {
            if(avoid.contains(request.getRequestURI())
                    || request.getRequestURI().contains("swagger") || X_XSRF_TOKEN != null){
                filterChain.doFilter(request, response);
                return;
            }

            throw new BadCredentialsException("Authorization Token missing..");
        }
        filterChain.doFilter(request, response);
    }
}
