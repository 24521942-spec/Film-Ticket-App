package com.example.ticketbookingapp.network.dto;

public class TicketHistoryDto {
    private int ticketId;
    private int bookingId;

    private String filmTitle;   // tên phim
    private String seatCode;    // ghế (C1, A4,...)
    private double totalPay;    // tổng tiền
    private String status;      // PAID / USED / ...
    private String qrCode;      // nếu cần
    private String showTime;    // ví dụ: 2025-12-30T18:00

    public TicketHistoryDto() {}

    public TicketHistoryDto(int ticketId, int bookingId, String filmTitle, String seatCode,
                            double totalPay, String status, String qrCode, String showTime) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.filmTitle = filmTitle;
        this.seatCode = seatCode;
        this.totalPay = totalPay;
        this.status = status;
        this.qrCode = qrCode;
        this.showTime = showTime;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getFilmTitle() { return filmTitle; }
    public void setFilmTitle(String filmTitle) { this.filmTitle = filmTitle; }

    public String getSeatCode() { return seatCode; }
    public void setSeatCode(String seatCode) { this.seatCode = seatCode; }

    public double getTotalPay() { return totalPay; }
    public void setTotalPay(double totalPay) { this.totalPay = totalPay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
}
