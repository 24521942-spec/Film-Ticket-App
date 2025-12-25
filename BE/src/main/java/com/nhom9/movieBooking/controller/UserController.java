package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.BookingBriefDto;
import com.nhom9.movieBooking.service.BookingQueryService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final BookingQueryService bookingQueryService;
    public UserController(BookingQueryService bookingQueryService) {
        this.bookingQueryService = bookingQueryService;
    }

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<BookingBriefDto>> myTickets(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingQueryService.getMyTickets(userId));
    }



}
