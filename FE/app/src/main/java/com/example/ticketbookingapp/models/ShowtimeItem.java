package com.example.ticketbookingapp.models;

public class ShowtimeItem {
    private int showtimeId;
    private String startTime;
    private double basePrice;
    private String cinemaName;
    private String roomName;

    public ShowtimeItem(int showtimeId, String startTime, double basePrice, String cinemaName, String roomName) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.basePrice = basePrice;
        this.cinemaName = cinemaName;
        this.roomName = roomName;
    }

    public int getShowtimeId() { return showtimeId; }
    public String getStartTime() { return startTime; }
    public double getBasePrice() { return basePrice; }
    public String getCinemaName() { return cinemaName; }
    public String getRoomName() { return roomName; }
}
