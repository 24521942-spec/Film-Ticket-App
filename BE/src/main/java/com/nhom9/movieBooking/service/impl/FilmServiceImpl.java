package com.nhom9.movieBooking.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.FilmDetailDto;
import com.nhom9.movieBooking.dto.FilmDto;
import com.nhom9.movieBooking.dto.MovieListDto;
import com.nhom9.movieBooking.mapper.FilmMapper;
import com.nhom9.movieBooking.model.Film;
import com.nhom9.movieBooking.repository.FilmRepository;
import com.nhom9.movieBooking.repository.ReviewRepository;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService{
    private final FilmRepository filmRepository;
    private final ShowTimeRepository showtimeRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, ShowTimeRepository showtimeRepository, ReviewRepository reviewRepository) {
        this.filmRepository = filmRepository;
        this.showtimeRepository = showtimeRepository;
        this.reviewRepository = reviewRepository;
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
    
    @Override
    public List<MovieListDto> getMovieListForHome() {
        
        Map<Integer, Float> avgMap = new HashMap<>();
        for (Object[] row : reviewRepository.avgRatingByFilm()) {
            Integer filmId = (Integer) row[0];
            Double avg = (Double) row[1];
            avgMap.put(filmId, avg == null ? 0f : avg.floatValue());
        }

        
        List<Film> films = filmRepository.findAll();
        return films.stream()
                .map(f -> new MovieListDto(
                        f.getFilmId(),
                        f.getGenre(),
                         f.getPosterUrl(),
                         avgMap.getOrDefault(f.getFilmId(), 0f),
                        f.getTitle()
                         
                ))
                .toList();

    }

    @Override
    public FilmDetailDto getFilmDetail(Integer filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Double avg = reviewRepository.avgRatingByFilmId(filmId);
        float rating = (avg == null) ? 0f : avg.floatValue();

        FilmDetailDto dto = new FilmDetailDto();
        dto.setFilmId(film.getFilmId());
        dto.setTitle(film.getTitle());
        dto.setGenre(film.getGenre());
        dto.setPosterUrl(film.getPosterUrl());
        dto.setRating(rating);

        dto.setDuration(film.getDuration());
        dto.setAgeRating(film.getAgeRating());
        dto.setDescription(film.getDescriptionFilm());
        dto.setDirector(film.getDirector());
        dto.setCast(film.getCastText());
        dto.setRelease(film.getReleaseDate() == null ? null : film.getReleaseDate().toString());


        return dto;
    }


}
