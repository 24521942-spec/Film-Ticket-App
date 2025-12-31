package com.nhom9.movieBooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.FilmDetailDto;
import com.nhom9.movieBooking.dto.MovieListDto;
import com.nhom9.movieBooking.dto.ShowtimeBriefDto;
import com.nhom9.movieBooking.model.ShowTime;
import com.nhom9.movieBooking.repository.ShowTimeRepository;
import com.nhom9.movieBooking.service.FilmService;
import com.nhom9.movieBooking.service.ShowtimeService;

@RestController
@RequestMapping("/api/films")
public class FilmPublicController {
    private final FilmService filmService;
    private final ShowtimeService showtimeService;
    private final ShowTimeRepository showtimeRepository;

    public FilmPublicController(FilmService filmService, ShowtimeService showtimeService,
            ShowTimeRepository showtimeRepository) {
        this.filmService = filmService;
        this.showtimeService = showtimeService;
        this.showtimeRepository = showtimeRepository;
    }
    // HOME – list phim
    @GetMapping("/home")
    public List<MovieListDto> getMovieListForHome() {
        return filmService.getMovieListForHome();
    }

    // FILM DETAIL
    @GetMapping("/{filmId}")
    public FilmDetailDto getFilmDetail(@PathVariable Integer filmId) {
        return filmService.getFilmDetail(filmId);
    }

    // SHOWTIME – group by cinema
    @GetMapping("/{filmId}/showtimes/sorted")
    public Map<String, List<LocalDateTime>> getShowtimeSorted(@PathVariable Integer filmId) {
        return showtimeService.getShowtimeByFilmGroupByCinema(filmId);
    }

    // SHOWTIME – brief list
    @GetMapping("/{filmId}/showtimes")
    public List<ShowtimeBriefDto> getShowtimesByFilm(@PathVariable Integer filmId) {
        List<ShowTime> showtimes = showtimeRepository.findByFilmFilmId(filmId);

        return showtimes.stream().map(st -> {
            String cinemaName = null;
            String roomName = null;

            if (st.getRoom() != null) {
                roomName = st.getRoom().getRoomName();
                if (st.getRoom().getCinema() != null) {
                    cinemaName = st.getRoom().getCinema().getCineName();
                }
            }

            return new ShowtimeBriefDto(
                    st.getShowTimeId(),
                    st.getStartTime() != null ? st.getStartTime().toString() : null,
                    st.getBasePrice(), // BigDecimal
                    cinemaName,
                    roomName
            );
        }).toList();
    }
}
