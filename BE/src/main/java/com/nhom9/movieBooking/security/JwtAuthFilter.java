package com.nhom9.movieBooking.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String email = jwtUtil.extractSubject(token);

                var auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    java.util.List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // ✅ LOG RA ĐỂ BIẾT TOKEN CÓ VẤN ĐỀ GÌ
                e.printStackTrace();
            }

        }

        filterChain.doFilter(request, response);
    }
}
