package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.CinemaDto;
import com.nhom9.movieBooking.model.Cinema;

public class CinemaMapper {
    public static CinemaDto toCinemaDto(Cinema cinema) {
        return new CinemaDto(cinema.getAddress(), cinema.getCineName(), cinema.getCinemaId(), cinema.getPhoneNumber());
    }
}

