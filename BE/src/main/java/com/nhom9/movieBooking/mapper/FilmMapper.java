package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.model.Film;

public class FilmMapper {
    public static FilmDto toFilmDto(Film film) {
        return new FilmDto(film.getAgeRating(), film.getDuration(), film.getFilmId(), film.getGenre(), film.getTitle(), film.getDescriptionFilm());
    }
}
