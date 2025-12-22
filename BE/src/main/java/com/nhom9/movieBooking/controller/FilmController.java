package com.nhom9.movieBooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.service.FilmService;
import com.nhom9.movieBooking.service.ShowtimeService;


@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final ShowtimeService showtimeService;
    private final FilmService filmService;

    public FilmController(FilmService filmService, ShowtimeService showtimeService) {
        this.filmService = filmService;
        this.showtimeService = showtimeService;
    }

    @GetMapping
    public List<FilmDto> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Integer id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public FilmDto createFilm(@RequestBody FilmDto filmDto) {
        return filmService.createFilm(filmDto);
    }

    @PutMapping("/{id}")
    public FilmDto updateFilm(@PathVariable Integer id, @RequestBody FilmDto filmDto) {
        return filmService.updateFilm(id, filmDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Integer id) {
        filmService.deleteFilm(id);
    }

    @GetMapping("/{filmId}/showtimes")
    public List<ShowTime> getShowtimeByFilm(@PathVariable Integer filmId) {
        return showtimeService.getAllShowtimeByFilmId(filmId);
    }

    @GetMapping("/{filmId}/showtimes/sorted")
    public Map<String, List<LocalDateTime>> getShowtimeByFilmSorted(@PathVariable Integer filmId) {
        return showtimeService.getShowtimeByFilmGroupByCinema(filmId);
    }



    
    
}
