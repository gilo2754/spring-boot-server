package com.pluralsight.security;

import com.pluralsight.exception.PersonNotFoundException;
import com.pluralsight.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PersonRepository personRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> personRepository.findByUsername(username)
                .orElseThrow(() -> new PersonNotFoundException("USer/Person ont found"));
        }
}
