package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nhom9.movieBooking.dto.BookingBriefDto;
import com.nhom9.movieBooking.dto.UserProfileDto;
import com.nhom9.movieBooking.model.User;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.BookingQueryService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final BookingQueryService bookingQueryService;
    private final UserRepository userRepository;

    public UserController(BookingQueryService bookingQueryService, UserRepository userRepository) {
        this.bookingQueryService = bookingQueryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<BookingBriefDto>> myTickets(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingQueryService.getMyTickets(userId));
    }

    // ✅ Profile theo userId (không cần Spring Security)
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Vì DTO của bạn constructor là (Integer, String, String)
        UserProfileDto dto = new UserProfileDto(
                user.getUserId(),      // đổi theo field thật: getId() hoặc getUserId()
                user.getFullName(),
                user.getEmail()
        );

        return ResponseEntity.ok(dto);
    }
}
