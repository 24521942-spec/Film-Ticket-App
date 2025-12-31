package com.nhom9.movieBooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.LoginRequest;
import com.nhom9.movieBooking.dto.LoginResponse;
import com.nhom9.movieBooking.dto.RegisterRequest;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        String email = req.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(email);
        user.setPhone(req.getPhone());
        user.setHashPw(passwordEncoder.encode(req.getPassword()));

        user.setRoleUser("USER");
        user.setGender(null);
        user.setAddress(null);

        userRepository.save(user);

        // ✅ AUTO LOGIN
        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                user.getRoleUser()
        ));
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        String email = req.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(401).body("Invalid email/password");

        if (!passwordEncoder.matches(req.getPassword(), user.getHashPw())) {
            return ResponseEntity.status(401).body("Invalid email/password");
        }

        String token = jwtUtil.generateToken(user.getEmail()); // subject = email

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                user.getRoleUser()
        ));
    }


}
