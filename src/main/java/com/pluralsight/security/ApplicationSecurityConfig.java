package com.pluralsight.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        ApplicationUserRole ADMIN = ApplicationUserRole.ADMIN;
        ApplicationUserRole USER = ApplicationUserRole.USER;
        ApplicationUserRole EXTERNAL = ApplicationUserRole.EXTERNAL;

        UserDetails saraStu = User.builder().username("stu").password(passwordEncoder().encode("pss"))//
            .roles(USER.name())//
            .authorities(USER.getGrantedAuthorities())//
            .build();

        UserDetails linadmin =
            User.builder().username("admin").password(passwordEncoder().encode("admin")).roles(ADMIN.name())
                // .authorities(ADMIN.getGrantedAuthorities())//
                .build();

        UserDetails tomext = User.builder().username("tom").password(passwordEncoder().encode("pss"))
            // .roles(EXTERNAL.name())
            .authorities(EXTERNAL.getGrantedAuthorities())//
            .build();

        return new InMemoryUserDetailsManager(linadmin, tomext, saraStu);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  http //
            .csrf().disable()//
            .authorizeRequests()//
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()//
      */
        http.csrf().disable()
                .cors() // Habilitar CORS
                .and()
                .authorizeRequests()
                .antMatchers("/basicauth/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic();

        // The order of the Matchers matters
            // Role-base
            // .antMatchers("/api/v1/**").hasRole(ApplicationUserRole.STUDENT.name())//
            // .antMatchers("admin/api/v1/**").hasAnyRole(ApplicationUserRole.ADMIN.name())//
            // Permission-based
            // .antMatchers(HttpMethod.DELETE, "admin/api/v1/**")
            // .hasAuthority(ApplicationUserPermision.USER_DELETE.getPermission())//
            // .antMatchers(HttpMethod.POST, "admin/api/v1/**")
            // .hasAuthority(ApplicationUserPermision.USER_CREATE.getPermission())//

       // http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Origen permitido
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        configuration.setExposedHeaders(Arrays.asList("authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}