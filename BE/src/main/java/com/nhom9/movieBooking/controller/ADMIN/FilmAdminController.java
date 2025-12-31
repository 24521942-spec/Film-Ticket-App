package com.nhom9.movieBooking.controller.ADMIN;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.service.FilmService;

@RestController
@RequestMapping("/api/admin/films")
public class FilmAdminController {
     private final FilmService filmService;

    public FilmAdminController(FilmService filmService) {
        this.filmService = filmService;
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

}
