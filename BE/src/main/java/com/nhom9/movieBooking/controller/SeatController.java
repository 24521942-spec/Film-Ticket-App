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
import com.nhom9.movieBooking.service.SeatService;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<SeatDto> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public SeatDto getSeatById(@PathVariable Integer id) {
        return seatService.getSeatById(id);
    }

    @PostMapping
    public SeatDto createSeat(@RequestBody SeatDto seatDto) {
        return seatService.createSeat(seatDto);
    }

    @PutMapping("/{id}")
    public SeatDto updateSeat(@PathVariable Integer id, @RequestBody SeatDto seatDto) {
        return seatService.updateSeat(id, seatDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSeat(@PathVariable Integer id) {
        seatService.deleteSeat(id);
    }

    @PostMapping("/{seatId}/hold/{showtimeId}/{userId}")
    public SeatDto holdSeat(
            @PathVariable Integer seatId,
            @PathVariable Integer showtimeId,
            @PathVariable Integer userId
    ) {
        return seatService.holdSeats(seatId, showtimeId, userId, 5);
    }


    

}
