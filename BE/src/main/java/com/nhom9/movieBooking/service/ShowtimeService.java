package com.nhom9.movieBooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeDto;
import com.nhom9.movieBooking.model.ShowTime;

@Service
public interface ShowtimeService {
    List<ShowtimeDto> getAllShowtimes();
    ShowtimeDto getShowtimeById(Integer id);
    ShowtimeDto createShowtime(ShowtimeDto showtimeDto);
    ShowtimeDto updateShowtime(Integer id, ShowtimeDto showtimeDto);
    void deleteShowtime(Integer id);
    List<ShowTime> getAllShowtimeByFilmId(Integer filmId);
    Map<String, List<LocalDateTime>> getShowtimeByFilmGroupByCinema(Integer filmId);
    List<SeatDto> holdSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes);
    List<SeatDto> getSeatsByShowtime(Integer showtimeId);
    List<SeatDto> releaseHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds);
    List<SeatDto> extendHoldSeats(Integer showtimeId, Integer userId, List<Integer> seatIds, int holdMinutes);

}
