package com.asc2526.da.unit5.vtschool_rest_api.security;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final StudentService studentService;

    public SecurityConfig(StudentService studentService) {
        this.studentService = studentService;
    }

    // authentication
    @Bean
    public UserDetailsService userDetailsService() {

        return username -> {

            if (username.equals("admin")) {
                return User
                        .withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
            }

            Student student = studentService
                    .findByEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found")
                    );

            return User
                    .withUsername(student.getEmail())
                    .password("{noop}" + student.getIdcard())
                    .roles("STUDENT")
                    .build();
        };
    }

    // authorization

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login", "/register", "/style.css").permitAll()

                        // ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // STUDENT
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {

                            boolean isAdmin = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));

                            if (isAdmin) {
                                response.sendRedirect("/admin");
                            } else {
                                response.sendRedirect("/student");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }
}