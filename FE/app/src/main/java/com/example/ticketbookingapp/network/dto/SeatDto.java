package com.example.ticketbookingapp.network.dto;

// SeatDto.java
public class SeatDto {
    public Integer seatId;
    public String seatCode;
    public String rowLabel;
    public Integer colLabel;
    public String seatType; // NORMAL/VIP (nhưng FE sẽ bỏ qua)
    public String status;   // SOLD/AVAILABLE/LOCKED/HOLD

    public SeatDto(Integer seatId, String seatCode, String rowLabel, Integer colLabel, String seatType, String status) {
        this.seatId = seatId;
        this.seatCode = seatCode;
        this.rowLabel = rowLabel;
        this.colLabel = colLabel;
        this.seatType = seatType;
        this.status = status;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public Integer getColLabel() {
        return colLabel;
    }

    public void setColLabel(Integer colLabel) {
        this.colLabel = colLabel;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

