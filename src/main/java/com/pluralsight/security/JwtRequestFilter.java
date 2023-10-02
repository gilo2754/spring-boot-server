package com.pluralsight.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    //@Autowired
    final private UserDetailsService userDetailsService;
    final private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtService.resolveToken(request);
            if (token != null && jwtService.validateToken(token)) {
                Authentication authentication = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // Maneja errores si es necesario
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String register = "/api/v1/person/add"; // Your login URL
        String loginUrl2 = "/api/v1/login"; // Your login URL
        //FIXME with this we disable JWT/Auth for the path URL
        //String pathURL = "/api/v1/"; // Your login URL
        String adminURL = "/admin/api/v1/";
        String h2Url = "/h2"; // URL of the H2 console
        String h2UrlConsole = "/h2-console"; // URL of the H2 console


        // Exclude the login URL and H2 console URL from filtering
        String requestUri = request.getRequestURI();
        return requestUri.equals(register) ||requestUri.equals(loginUrl2) ||
                requestUri.startsWith(h2UrlConsole) ||
                //FIXME and this too:
               // requestUri.startsWith(pathURL) ||
                requestUri.startsWith(adminURL)||
                requestUri.startsWith(h2Url);
    }
    }


