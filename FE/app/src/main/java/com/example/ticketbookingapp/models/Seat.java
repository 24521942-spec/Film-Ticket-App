package com.example.ticketbookingapp.models;

public class Seat {
    private String id; // Ví dụ: "A1", "A2"
    private int status; // 0: Available, 1: Occupied, 2: Selected

    public Seat(String id, int status) {
        this.id = id;
        this.status = status;
    }

    public String getId() { return id; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}