package com.nhom9.movieBooking.service;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.BookingRequestDto;
import com.nhom9.movieBooking.dto.BookingResponseDto;

@Service
public interface BookingService {
    BookingResponseDto checkoutFromHold(BookingRequestDto req);
}
