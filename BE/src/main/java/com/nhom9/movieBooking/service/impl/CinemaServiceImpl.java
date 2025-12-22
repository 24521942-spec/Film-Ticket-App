package com.nhom9.movieBooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.CinemaDto;
import com.nhom9.movieBooking.mapper.CinemaMapper;
import com.nhom9.movieBooking.model.Cinema;
import com.nhom9.movieBooking.repository.CinemaRepository;
import com.nhom9.movieBooking.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public List<CinemaDto> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream()
                .map(CinemaMapper::toCinemaDto)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDto getCinemaById(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
        return CinemaMapper.toCinemaDto(cinema);
    }

    @Override
    public CinemaDto createCinema(CinemaDto cinemaDto) {
        Cinema cinema = new Cinema();
        cinema.setCineName(cinemaDto.getCineName());
        cinema.setAddress(cinemaDto.getAddress());
        cinema.setPhoneNumber(cinemaDto.getPhoneNumber());
        cinemaRepository.save(cinema);
        return CinemaMapper.toCinemaDto(cinema);
    }

    @Override
    public CinemaDto updateCinema(Integer id, CinemaDto cinemaDto) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
        cinema.setCineName(cinemaDto.getCineName());
        cinema.setAddress(cinemaDto.getAddress());
        cinema.setPhoneNumber(cinemaDto.getPhoneNumber());
        cinemaRepository.save(cinema);
        return CinemaMapper.toCinemaDto(cinema);
    }

    @Override
    public void deleteCinema(Integer id) {
        cinemaRepository.deleteById(id);
    }


}
