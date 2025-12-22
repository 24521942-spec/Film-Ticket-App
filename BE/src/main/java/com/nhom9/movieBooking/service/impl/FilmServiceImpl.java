package com.nhom9.movieBooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.mapper.FilmMapper;
import com.nhom9.movieBooking.model.Film;
import com.nhom9.movieBooking.repository.FilmRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService{
    private final FilmRepository filmRepository;
    private final ShowTimeRepository showtimeRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, ShowTimeRepository showtimeRepository) {
        this.filmRepository = filmRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmRepository.findAll(); 
        return films.stream()
                .map(FilmMapper::toFilmDto)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDto getFilmById(Integer id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film not found"));
        return FilmMapper.toFilmDto(film);
    }

    @Override
    public FilmDto createFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setTitle(filmDto.getTitle());
        film.setGenre(filmDto.getGenre());
        film.setDuration(filmDto.getDuration());
        film.setAgeRating(filmDto.getAgeRating());
        film.setDescriptionFilm(filmDto.getDescription());
        film.setGenre(filmDto.getGenre());
        filmRepository.save(film);
        return FilmMapper.toFilmDto(film);

    }

    @Override
    public FilmDto updateFilm(Integer id, FilmDto filmDto) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film not found"));
        film.setTitle(filmDto.getTitle());
        film.setGenre(filmDto.getGenre());
        film.setDuration(filmDto.getDuration());
        film.setAgeRating(filmDto.getAgeRating());
        film.setDescriptionFilm(filmDto.getDescription());
        film.setGenre(filmDto.getGenre());
        filmRepository.save(film);
        return FilmMapper.toFilmDto(film);
    }

    @Override
    public void deleteFilm(Integer id) {
       filmRepository.deleteById(id);
    }
    

}
