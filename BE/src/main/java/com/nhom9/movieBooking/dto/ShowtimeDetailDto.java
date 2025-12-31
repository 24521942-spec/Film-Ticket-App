package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;

public class ShowtimeDetailDto {
    private Integer showtimeId;
    private Integer filmId;
    private String filmTitle;

    private Integer roomId;
    private String roomName;

    private Integer cinemaId;
    private String cinemaName;

    private String startTime; // String để FE dễ dùng
    private BigDecimal basePrice;

    public ShowtimeDetailDto() {}

    public ShowtimeDetailDto(Integer showtimeId, Integer filmId, String filmTitle, Integer roomId, String roomName,
            Integer cinemaId, String cinemaName, String startTime, BigDecimal basePrice) {
        this.showtimeId = showtimeId;
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.roomId = roomId;
        this.roomName = roomName;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.startTime = startTime;
        this.basePrice = basePrice;
    }

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    
}
