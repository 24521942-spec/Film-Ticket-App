package com.nhom9.movieBooking.dto;

public class TicketDto {
    private int ticketId;
    private int bookingId;
    private int seatId;
    private String status;
    private String qrCode;

    public TicketDto(int bookingId, String qrCode, int seatId, String status, int ticketId) {
        this.bookingId = bookingId;
        this.qrCode = qrCode;
        this.seatId = seatId;
        this.status = status;
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }



}
