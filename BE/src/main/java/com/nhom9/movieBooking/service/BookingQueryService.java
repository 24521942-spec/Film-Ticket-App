package com.nhom9.movieBooking.service;

import java.util.List;

import com.nhom9.movieBooking.dto.BookingBriefDto;

public interface BookingQueryService {
    List<BookingBriefDto> getMyTickets(Integer userId);
}
