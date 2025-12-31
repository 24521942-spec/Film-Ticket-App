package com.example.ticketbookingapp.network.dto;

import java.util.List;

public class HoldRequest {
    public Integer userId;
    public List<Integer> seatIds;
    public Integer holdMinutes;

    public HoldRequest(Integer userId, List<Integer> seatIds, Integer holdMinutes) {
        this.userId = userId;
        this.seatIds = seatIds;
        this.holdMinutes = holdMinutes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public Integer getHoldMinutes() {
        return holdMinutes;
    }

    public void setHoldMinutes(Integer holdMinutes) {
        this.holdMinutes = holdMinutes;
    }
}
