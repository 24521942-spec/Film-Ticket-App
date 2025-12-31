package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;

public class ShowtimeDetailDto {
    @SerializedName("showtimeId")
    private Integer showtimeId;

    @SerializedName("startTime")
    private String startTime; // VD: "2025-12-25T20:30:00" hoặc "20:30"

    @SerializedName("cinemaId")
    private Integer cinemaId;

    @SerializedName("cinemaName")
    private String cinemaName;

    @SerializedName("cinemaAddress")
    private String cinemaAddress;

    @SerializedName("roomName")
    private String roomName;

    // (tuỳ BE) giá vé nếu BE trả

    private Double basePrice;


    public ShowtimeDetailDto(Integer showtimeId, String startTime, Integer cinemaId, String cinemaName, String cinemaAddress, String roomName, Double basePrice) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.roomName = roomName;
        this.basePrice = basePrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


}
