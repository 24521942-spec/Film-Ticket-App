package com.nhom9.movieBooking.enums;

import java.util.List;

public class HoldSeatsRequest {
    private Integer userId;
    private List<Integer> seatIds;
    private Integer holdMinutes;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }

    public Integer getHoldMinutes() { return holdMinutes; }
    public void setHoldMinutes(Integer holdMinutes) { this.holdMinutes = holdMinutes;}


}

