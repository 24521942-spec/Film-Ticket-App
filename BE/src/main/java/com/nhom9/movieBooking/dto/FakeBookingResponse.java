package com.nhom9.movieBooking.dto;

public class FakeBookingResponse {
    public int bookingId;
    public String qrUrl;
    public FakeBookingResponse(int bookingId, String qrUrl) {
        this.bookingId = bookingId;
        this.qrUrl = qrUrl;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public String getQrUrl() {
        return qrUrl;
    }
    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    
}
