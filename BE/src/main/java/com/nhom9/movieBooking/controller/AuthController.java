package com.nhom9.movieBooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.AuthResponseDto;
import com.nhom9.movieBooking.dto.LoginRequestDto;
import com.nhom9.movieBooking.dto.RegisterRequestDto;
import com.nhom9.movieBooking.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(
        value = "/register",
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto req) {
        return ResponseEntity.ok(authService.register(req));
    }


    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto req) {
        return authService.login(req);
    }
}
