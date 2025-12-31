package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nhom9.movieBooking.enums.BookingStatus;

public class TicketHistoryDto {
    private Long ticketId;
    private Long bookingId;
    private String filmTitle;
    private String seatCode;
    private BigDecimal totalPay;
    private String status;
    private String qrCode;
    private String showTime;

    public TicketHistoryDto() {}

    public TicketHistoryDto(
            Long ticketId,
            Long bookingId,
            String filmTitle,
            String seatCode,
            BigDecimal totalPay,
            BookingStatus statusBooking,
            String qrCode,
            LocalDateTime startTime
    ) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.filmTitle = filmTitle;
        this.seatCode = seatCode;
        this.totalPay = totalPay;
        this.status = (statusBooking != null) ? statusBooking.name() : null;
        this.qrCode = qrCode;
        this.showTime = (startTime != null)
                ? startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
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

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }



}