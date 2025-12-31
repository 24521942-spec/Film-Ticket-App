package com.nhom9.movieBooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeCreateUpdateDto;
import com.nhom9.movieBooking.dto.ShowtimeDetailDto;

@Service
public interface ShowtimeService {
    // ===== ADMIN =====
    List<ShowtimeDetailDto> getAllShowtimes();
    ShowtimeDetailDto getShowtimeDetail(Integer showtimeId);
    ShowtimeDetailDto createShowtime(ShowtimeCreateUpdateDto dto);
    ShowtimeDetailDto updateShowtime(Integer showtimeId, ShowtimeCreateUpdateDto dto);
    void deleteShowtime(Integer showtimeId);

    // ===== PUBLIC / APP =====
    List<ShowtimeDetailDto> getShowtimesByFilm(Integer filmId);
    List<ShowtimeDetailDto> getShowtimesByCinema(Integer cinemaId);
    Map<String, List<LocalDateTime>> getShowtimeByFilmGroupByCinema(Integer filmId);

    // ===== SEAT =====
    List<SeatDto> getSeatsByShowtime(Integer showtimeId);
    List<SeatDto> holdSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes);
    List<SeatDto> releaseHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds);
    List<SeatDto> extendHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes);


}
