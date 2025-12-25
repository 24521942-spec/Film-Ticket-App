package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;

public class ShowtimeBriefDto {
    private Integer showtimeId;
    private String startTime;      
    private BigDecimal basePrice;

    private String cinemaName;
    private String roomName;

    public ShowtimeBriefDto() {}

    public ShowtimeBriefDto(Integer showtimeId, String startTime, BigDecimal basePrice, String cinemaName, String roomName) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.basePrice = basePrice;
        this.cinemaName = cinemaName;
        this.roomName = roomName;
    }

    public Integer getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Integer showtimeId) { this.showtimeId = showtimeId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getCinemaName() { return cinemaName; }
    public void setCinemaName(String cinemaName) { this.cinemaName = cinemaName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
}
