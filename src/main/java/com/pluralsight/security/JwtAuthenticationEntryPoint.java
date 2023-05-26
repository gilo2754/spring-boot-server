package com.pluralsight.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // This method will be invoked when an unauthenticated user tries to access a secured resource
        // You can customize the behavior here, such as returning a specific HTTP status code or error message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized babe");
    }
}

