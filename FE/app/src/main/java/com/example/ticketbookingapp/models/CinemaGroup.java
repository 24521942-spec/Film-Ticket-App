package com.example.ticketbookingapp.models;

import java.util.ArrayList;
import java.util.List;

public class CinemaGroup {
    public Integer cinemaId;
    public String cinemaName;
    public String address;
    public List<SessionItem> sessions = new ArrayList<>();

    public CinemaGroup(Integer cinemaId, String cinemaName, String address, List<SessionItem> sessions) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.address = address;
        this.sessions = sessions;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SessionItem> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionItem> sessions) {
        this.sessions = sessions;
    }
}
