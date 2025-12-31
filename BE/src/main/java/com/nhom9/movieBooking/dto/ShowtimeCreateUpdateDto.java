package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShowtimeCreateUpdateDto {
    private Integer filmId;
    private Integer roomId;
    private LocalDateTime startTime;
    private BigDecimal basePrice;
    private String languageFilm;  // <-- thêm
    private String subtitle;

    public ShowtimeCreateUpdateDto() {}

    

    public ShowtimeCreateUpdateDto(Integer filmId, Integer roomId, LocalDateTime startTime, BigDecimal basePrice,
            String languageFilm, String subtitle) {
        this.filmId = filmId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.basePrice = basePrice;
        this.languageFilm = languageFilm;
        this.subtitle = subtitle;
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

    

    // getters/setters...
}
