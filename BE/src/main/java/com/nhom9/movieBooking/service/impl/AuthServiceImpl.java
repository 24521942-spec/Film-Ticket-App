package com.nhom9.movieBooking.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.AuthResponseDto;
import com.nhom9.movieBooking.dto.LoginRequestDto;
import com.nhom9.movieBooking.dto.RegisterRequestDto;
import com.nhom9.movieBooking.exception.EmailAlreadyExistsException;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setHashPw(passwordEncoder.encode(req.getPassword()));
        user.setRoleUser("USER");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new AuthResponseDto(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRoleUser()
        );
    }

    @Override
    public AuthResponseDto login(LoginRequestDto req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getHashPw())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new AuthResponseDto(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRoleUser()
        );
    }
}
