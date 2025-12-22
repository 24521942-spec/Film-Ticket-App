package com.nhom9.movieBooking.dto;

import com.nhom9.movieBooking.enums.SeatStatus;

public class SeatDto {
    private int seatId;             
    private String seatCode;        
    private String rowLabel;        
    private int colLabel;           
    private String seatType;
    private SeatStatus status;

    public SeatDto(int colLabel, String rowLabel, String seatCode, int seatId, String seatType, SeatStatus status) {
        this.colLabel = colLabel;
        this.rowLabel = rowLabel;
        this.seatCode = seatCode;
        this.seatId = seatId;
        this.seatType = seatType;
        this.status = status;
    }

    
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
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

    public int getColLabel() {
        return colLabel;
    }

    public void setColLabel(int colLabel) {
        this.colLabel = colLabel;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }


}
