package com.nhom9.movieBooking.dto;

public class CinemaDto {
    private int cinemaID;
    private String cineName;
    private String Address;
    private String phoneNumber;

    public CinemaDto(String Address, String cineName, int cinemaID, String phoneNumber) {
        this.Address = Address;
        this.cineName = cineName;
        this.cinemaID = cinemaID;
        this.phoneNumber = phoneNumber;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getCineName() {
        return cineName;
    }

    public void setCineName(String cineName) {
        this.cineName = cineName;
    }

}
