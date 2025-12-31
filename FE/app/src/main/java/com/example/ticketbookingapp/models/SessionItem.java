package com.example.ticketbookingapp.models;

public class SessionItem {
    public Integer showtimeId;
    public String startTime;
    public String roomName;

    public Integer adult;
    public Integer child;
    public Integer student;
    public Integer vip;

    public SessionItem(Integer showtimeId, String startTime, String roomName, Integer adult, Integer child, Integer student, Integer vip) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.roomName = roomName;
        this.adult = adult;
        this.child = child;
        this.student = student;
        this.vip = vip;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }
}
