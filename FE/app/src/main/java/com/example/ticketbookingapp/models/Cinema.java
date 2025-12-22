package com.example.ticketbookingapp.models;
import java.util.List;

public class Cinema {
    private String name;
    private String address;
    private String distance;
    private List<Session> sessions;

    public Cinema(String name, String address, String distance, List<Session> sessions) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.sessions = sessions;
    }
    // Getter & Setter...
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getDistance() { return distance; }
    public List<Session> getSessions() { return sessions; }
}