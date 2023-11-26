package com.pluralsight.security;

import com.pluralsight.exception.UserNotFoundException;
import com.pluralsight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User/Person ont found"));
        }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static final int MAX_CLINICS_ALLOWED = 2; // Puedes ajustar este valor según tus necesidades

}
