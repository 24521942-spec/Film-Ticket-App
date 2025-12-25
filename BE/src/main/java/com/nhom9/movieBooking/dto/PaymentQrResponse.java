package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;

public class PaymentQrResponse {
    private Integer bookingId;
    private BigDecimal amount;
    private String qrContent;      
    private String qrBase64Png; 

    public PaymentQrResponse() {}

    public PaymentQrResponse(Integer bookingId, BigDecimal amount, String qrContent, String qrBase64Png) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.qrContent = qrContent;
        this.qrBase64Png = qrBase64Png;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }

    public String getQrBase64Png() {
        return qrBase64Png;
    }

    public void setQrBase64Png(String qrBase64Png) {
        this.qrBase64Png = qrBase64Png;
    }

    
}
