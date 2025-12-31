package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShowtimeDto {
    private int showtimeId;
    private LocalDateTime startTime;
    private BigDecimal basePrice;
    private String languageFilm;
    private String subtitle;
    
    private Integer filmId;
    private Integer roomId;

    public ShowtimeDto() {}
    
    

    public Integer getFilmId() {
        return filmId;
    }



    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }



    public Integer getRoomId() {
        return roomId;
    }



    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }



    public ShowtimeDto(int showtimeId, LocalDateTime startTime, BigDecimal basePrice, String languageFilm,
            String subtitle, Integer filmId, Integer roomId) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.basePrice = basePrice;
        this.languageFilm = languageFilm;
        this.subtitle = subtitle;
        this.filmId = filmId;
        this.roomId = roomId;
    }



    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getLanguageFilm() {
        return languageFilm;
    }

    public void setLanguageFilm(String languageFilm) {
        this.languageFilm = languageFilm;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
