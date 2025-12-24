package com.nhom9.movieBooking.service;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.AuthResponseDto;
import com.nhom9.movieBooking.dto.LoginRequestDto;
import com.nhom9.movieBooking.dto.RegisterRequestDto;


@Service
public interface AuthService {
    AuthResponseDto register(RegisterRequestDto req);
    AuthResponseDto login(LoginRequestDto req);
}
