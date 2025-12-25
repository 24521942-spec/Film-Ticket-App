package com.nhom9.movieBooking.dto;

public class BookingBriefDto {
    private Integer bookingId;
    private String filmTitle;
    private String cinemaName;
    private String showTime;  
    private String status;
    private String posterUrl;

    public BookingBriefDto() {}

    public BookingBriefDto(Integer bookingId, String cinemaName, String filmTitle, String posterUrl, String showTime, String status) {
        this.bookingId = bookingId;
        this.cinemaName = cinemaName;
        this.filmTitle = filmTitle;
        this.posterUrl = posterUrl;
        this.showTime = showTime;
        this.status = status;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


}
