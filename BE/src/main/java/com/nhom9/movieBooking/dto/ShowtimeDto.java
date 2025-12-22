package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShowtimeDto {
    private int showtimeId;
    private LocalDateTime startTime;
    private BigDecimal basePrice;
    private String languageFilm;
    private String subtitle;

    public ShowtimeDto(BigDecimal basePrice, String languageFilm, int showtimeId, LocalDateTime startTime, String subtitle) {
        this.basePrice = basePrice;
        this.languageFilm = languageFilm;
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.subtitle = subtitle;
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
