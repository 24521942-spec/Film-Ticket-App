package com.nhom9.movieBooking.dto;

import java.util.List;

public class BookingResponseDto {
    private int bookingId;
    private int userId;
    private int showtimeId;
    private List<SeatDto> seats;
    private String status;
    private String paymentStatus;

    public BookingResponseDto(int bookingId, String paymentStatus, List<SeatDto> seats, int showtimeId, String status, int userId) {
        this.bookingId = bookingId;
        this.paymentStatus = paymentStatus;
        this.seats = seats;
        this.showtimeId = showtimeId;
        this.status = status;
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    
    
}
