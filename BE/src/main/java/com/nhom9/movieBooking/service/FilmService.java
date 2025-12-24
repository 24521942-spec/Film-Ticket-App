package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.FilmDetailDto;
import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.dto.MovieListDto;

@Service
public interface FilmService {
    List<FilmDto> getAllFilms();
    FilmDto getFilmById(Integer id);
    FilmDto createFilm(FilmDto filmDto);
    FilmDto updateFilm(Integer id, FilmDto filmDto);
    void deleteFilm(Integer id);
    List<MovieListDto> getMovieListForHome();
    FilmDetailDto getFilmDetail(Integer filmId);
}
