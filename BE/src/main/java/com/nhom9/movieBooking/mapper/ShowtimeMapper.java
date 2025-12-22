package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.ShowtimeDto;
import com.nhom9.movieBooking.model.ShowTime;

public class ShowtimeMapper {
    public static ShowtimeDto toShowtimeDto(ShowTime showtime) {
        return new ShowtimeDto(showtime.getBasePrice(), showtime.getLanguageFilm(), showtime.getShowTimeId(), showtime.getStartTime(), showtime.getSubtitle());
    }
}
