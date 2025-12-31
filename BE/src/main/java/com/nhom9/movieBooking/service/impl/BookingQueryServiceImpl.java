package com.nhom9.movieBooking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhom9.movieBooking.dto.BookingBriefDto;
import com.nhom9.movieBooking.repository.BookingRepository;
import com.nhom9.movieBooking.repository.UserRepository;
import com.nhom9.movieBooking.service.BookingQueryService;

@Service
public class BookingQueryServiceImpl implements BookingQueryService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingQueryServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingBriefDto> getMyTickets(Integer userId) {
        if (userId == null) throw new RuntimeException("userId is required");

        
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        return bookingRepository.findBriefByUserId(userId);
    }
}
