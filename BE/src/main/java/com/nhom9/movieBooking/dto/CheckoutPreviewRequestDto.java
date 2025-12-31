package com.nhom9.movieBooking.dto;

import java.util.List;

public class CheckoutPreviewRequestDto {
    private Integer userId;
    private List<Integer> seatIds;
    private List<FoodItemDto> foodItems;

    public static class FoodItemDto {
        private Integer foodId;
        private Integer quantity;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }

    public List<FoodItemDto> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodItemDto> foodItems) { this.foodItems = foodItems; }
}
