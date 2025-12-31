package com.nhom9.movieBooking.dto;

import java.util.List;

public class BookingRequestDto {
    private Integer userId;
    private Integer showtimeId;
    private List<Integer> seatIds;
    private List<FoodOrderItemDto> foodItems;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Integer showtimeId) { this.showtimeId = showtimeId; }

    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }

    public List<FoodOrderItemDto> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodOrderItemDto> foodItems) {
        this.foodItems = foodItems;
    }

    
}

