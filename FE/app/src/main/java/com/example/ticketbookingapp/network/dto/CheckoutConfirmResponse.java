package com.example.ticketbookingapp.network.dto;

public class CheckoutConfirmResponse {
    public int bookingId;
    public int totalAmount;
    public String message;

    public CheckoutConfirmResponse(int bookingId, int totalAmount, String message) {
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
        this.message = message;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
