package com.pluralsight.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
    "jwt.secretKey=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970",
    "jwt.expiration=86400000"
})
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testGenerateToken() {
        UserDetails userDetails = User.builder()
                .username("john.doe")
                .password("password")
                .roles("PATIENT")
                .build();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }
}
