package com.example.ticketbookingapp.network.dto;

import java.util.List;

public class CheckoutConfirmRequest {
    public int userId;
    public List<Integer> seatIds;
    public List<FoodItemReq> foodItems;


    public CheckoutConfirmRequest(int userId, List<Integer> seatIds, List<FoodItemReq> foodItems) {
        this.userId = userId;
        this.seatIds = seatIds;
        this.foodItems = foodItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public List<FoodItemReq> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItemReq> foodItems) {
        this.foodItems = foodItems;
    }
}
