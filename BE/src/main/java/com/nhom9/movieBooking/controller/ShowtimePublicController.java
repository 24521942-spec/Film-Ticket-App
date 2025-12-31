package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.SeatHoldActionRequest;
import com.nhom9.movieBooking.dto.ShowtimeDetailDto;
import com.nhom9.movieBooking.service.ShowtimeService;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimePublicController {

    private final ShowtimeService showtimeService;

    public ShowtimePublicController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    // GET /api/showtimes/film/3
    @GetMapping("/film/{filmId}")
    public List<ShowtimeDetailDto> getShowtimesByFilm(@PathVariable Integer filmId) {
        return showtimeService.getShowtimesByFilm(filmId);
    }

    // GET /api/showtimes/cinema/1
    @GetMapping("/cinema/{cinemaId}")
    public List<ShowtimeDetailDto> getShowtimesByCinema(@PathVariable Integer cinemaId) {
        return showtimeService.getShowtimesByCinema(cinemaId);
    }

    // GET /api/showtimes/1
    @GetMapping("/{showtimeId}")
    public ShowtimeDetailDto getShowtimeDetail(@PathVariable Integer showtimeId) {
        return showtimeService.getShowtimeDetail(showtimeId);
    }

    // GET /api/showtimes/1/seats
    @GetMapping("/{showtimeId}/seats")
    public List<SeatDto> getSeats(@PathVariable Integer showtimeId) {
        return showtimeService.getSeatsByShowtime(showtimeId);
    }
        // POST /api/showtimes/1/hold
    @PostMapping("/{showtimeId}/hold")
    public List<SeatDto> holdSeats(
            @PathVariable Integer showtimeId,
            @RequestBody SeatHoldActionRequest req
    ) {
        int minutes = (req.getHoldMinutes() == null ? 5 : req.getHoldMinutes());
        return showtimeService.holdSeats(showtimeId, req.getUserId(), req.getSeatIds(), minutes);
    }

    // POST /api/showtimes/1/release
    @PostMapping("/{showtimeId}/release")
    public List<SeatDto> releaseHold(
            @PathVariable Integer showtimeId,
            @RequestBody SeatHoldActionRequest req
    ) {
        return showtimeService.releaseHoldSeats(showtimeId, req.getUserId(), req.getSeatIds());
    }

    // POST /api/showtimes/1/extend
    @PostMapping("/{showtimeId}/extend")
    public List<SeatDto> extendHold(
            @PathVariable Integer showtimeId,
            @RequestBody SeatHoldActionRequest req
    ) {
        int minutes = (req.getHoldMinutes() == null ? 5 : req.getHoldMinutes());
        return showtimeService.extendHoldSeats(showtimeId, req.getUserId(), req.getSeatIds(), minutes);
    }

}
