package com.example.ticketbookingapp.models;

public class Seat {
    private int seatId;
    private String seatCode; // "A1"
    private String status;

    public Seat(int seatId, String seatCode, String status) {
        this.seatId = seatId;
        this.seatCode = seatCode;
        this.status = status;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}