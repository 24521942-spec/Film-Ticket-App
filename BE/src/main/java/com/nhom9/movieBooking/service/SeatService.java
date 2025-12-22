package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;

@Service
public interface  SeatService {
    List<SeatDto> getAllSeats();
    SeatDto getSeatById(Integer id);
    SeatDto createSeat(SeatDto seatDto);
    SeatDto updateSeat(Integer id, SeatDto seatDto);
    void deleteSeat(Integer id);
    List<SeatDto> getSeatByShowtime(Integer showtimeId);
    List<SeatDto> holdSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes);
    
}
