package com.pluralsight.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtServiceTest {

    @Test
    public void testGenerateToken() {
        // Preparación de datos de prueba
        UserDetails userDetails = User.builder()
                .username("john.doe")
                .password("password")
                .roles("PATIENT")
                .build();

        // Creación del objeto JwtService
        JwtService jwtService = new JwtService();

        // Generación del token
        String token = jwtService.generateToken(userDetails);

        // Verificación de resultados
        assertNotNull(token);
        // Aquí puedes realizar más aserciones sobre el token generado si es necesario
    }
}
