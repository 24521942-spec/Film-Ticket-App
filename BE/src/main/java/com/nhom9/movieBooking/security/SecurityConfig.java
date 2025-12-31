package com.nhom9.movieBooking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // ✅ BẮT BUỘC
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/error",                 // ✅ THÊM DÒNG NÀY
                    "/api/auth/**",
                    "/api/users/**",
                    "/api/films/**",
                    "/api/showtimes/*/checkout/confirm"
                ).permitAll()
                .anyRequest().authenticated()
            );

        http.addFilterBefore(
            new JwtAuthFilter(jwtUtil),
            UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}
