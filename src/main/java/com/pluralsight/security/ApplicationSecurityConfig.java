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
        http //
            .csrf().disable()//
            .authorizeRequests()//
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()//
            // The order of the Matchers matters
            // Role-base
            // .antMatchers("/api/v1/**").hasRole(ApplicationUserRole.STUDENT.name())//
            // .antMatchers("admin/api/v1/**").hasAnyRole(ApplicationUserRole.ADMIN.name())//
            // Permission-based
            // .antMatchers(HttpMethod.DELETE, "admin/api/v1/**")
            // .hasAuthority(ApplicationUserPermision.USER_DELETE.getPermission())//
            // .antMatchers(HttpMethod.POST, "admin/api/v1/**")
            // .hasAuthority(ApplicationUserPermision.USER_CREATE.getPermission())//
            .anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}