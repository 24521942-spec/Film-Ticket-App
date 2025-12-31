package com.nhom9.movieBooking.dto;

import java.util.List;

public class SeatHoldActionRequest {
    private Integer userId;
    private List<Integer> seatIds;
    private Integer holdMinutes; 

    public SeatHoldActionRequest(Integer holdMinutes, List<Integer> seatIds, Integer userId) {
        this.holdMinutes = holdMinutes;
        this.seatIds = seatIds;
        this.userId = userId;
    }


    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }

    public Integer getHoldMinutes() { return holdMinutes; }
    public void setHoldMinutes(Integer holdMinutes) { this.holdMinutes = holdMinutes; }
}
