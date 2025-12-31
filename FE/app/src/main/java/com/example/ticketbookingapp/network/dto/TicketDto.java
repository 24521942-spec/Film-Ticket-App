package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TicketDto implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("qrCode")
    public String qrCode;

    @SerializedName("bookingId")
    public int bookingId;

    @SerializedName("seatCode")
    public String seatCode;

    @SerializedName("status")
    public int status; // nếu BE đang trả int (0/1/2...)

    // optional constructor rỗng (Gson không bắt buộc, nhưng thêm cũng ok)
    public TicketDto() {}

    public TicketDto(int id, String qrCode, int bookingId, String seatCode, int status) {
        this.id = id;
        this.qrCode = qrCode;
        this.bookingId = bookingId;
        this.seatCode = seatCode;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
