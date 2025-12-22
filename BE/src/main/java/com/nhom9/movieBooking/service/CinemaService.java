package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.CinemaDto;

@Service
public interface CinemaService {
    List<CinemaDto> getAllCinemas();
    CinemaDto getCinemaById(Integer id);
    CinemaDto createCinema(CinemaDto cinemaDto);
    CinemaDto updateCinema(Integer id, CinemaDto cinemaDto);
    void deleteCinema(Integer id);
}
