package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.SeatDto;
import com.nhom9.movieBooking.dto.ShowtimeDto;
import com.nhom9.movieBooking.enums.HoldSeatsRequest;
import com.nhom9.movieBooking.service.SeatService;
import com.nhom9.movieBooking.service.ShowtimeService;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;
    private final SeatService seatService;

    public ShowtimeController(ShowtimeService showtimeService, SeatService seatService) {
    this.showtimeService = showtimeService;
    this.seatService = seatService;
}

    @GetMapping
    public List<ShowtimeDto> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    @GetMapping("/{id}")
    public ShowtimeDto getShowtimeById(@PathVariable Integer id) {
        return showtimeService.getShowtimeById(id);
    }

    @PostMapping
    public ShowtimeDto createShowtime(@RequestBody ShowtimeDto showtimeDto) {
        return showtimeService.createShowtime(showtimeDto);
    }

    @PutMapping("/{id}")
    public ShowtimeDto updateShowtime(@PathVariable Integer id, @RequestBody ShowtimeDto showtimeDto) {
        return showtimeService.updateShowtime(id, showtimeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteShowtime(@PathVariable Integer id) {
        showtimeService.deleteShowtime(id);
    }

    @GetMapping("/{showtimeId}/seats")
    public List<SeatDto> getSeatsByShowtime(@PathVariable Integer showtimeId) {
        return showtimeService.getSeatsByShowtime(showtimeId);
    }

    @PostMapping("/{showtimeId}/seats/hold")
    public List<SeatDto> holdSeats(@PathVariable Integer showtimeId,
                                @RequestBody HoldSeatsRequest req) {
        int holdMinutes = (req.getHoldMinutes() == null ? 5 : req.getHoldMinutes());
        return seatService.holdSeats(showtimeId, req.getUserId(), req.getSeatIds(), holdMinutes);
    }



}
