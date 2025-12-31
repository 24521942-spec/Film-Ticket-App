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

import com.nhom9.movieBooking.dto.CinemaDto;
import com.nhom9.movieBooking.dto.RoomDto;
import com.nhom9.movieBooking.service.CinemaService;
import com.nhom9.movieBooking.service.RoomService;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {
    private final CinemaService cinemaService;
    private final RoomService roomService;

    public CinemaController(CinemaService cinemaService, RoomService roomService) {
        this.cinemaService = cinemaService;
        this.roomService = roomService;
    }

    
    @GetMapping
    public List<CinemaDto> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/{id}")
    public CinemaDto getCinemaById(@PathVariable Integer id) {
        return cinemaService.getCinemaById(id);
    }

    @PostMapping
    public CinemaDto createCinema(@RequestBody CinemaDto cinemaDto) {
        return cinemaService.createCinema(cinemaDto);
    }

    @PutMapping("/{id}")
    public CinemaDto updateCinema(@PathVariable Integer id, @RequestBody CinemaDto cinemaDto) {
        return cinemaService.updateCinema(id, cinemaDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCinema(@PathVariable Integer id) {
        cinemaService.deleteCinema(id);
    }

    @GetMapping("/{cinemaId}/rooms")
    public List<RoomDto> getRoomsByCinema(@PathVariable Integer cinemaId) {
        return roomService.getRoomsByCinemaDtos(cinemaId);
    }
    
}
