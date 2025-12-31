package com.nhom9.movieBooking.dto;

import java.util.List;

public class FakeBookingRequestDto {

    private Integer showtimeId;
    private Integer userId; // ✅ thêm

    private List<Integer> seatIds;

    private List<FoodItem> foods; // ✅ thêm

    public Integer getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Integer showtimeId) { this.showtimeId = showtimeId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Integer> seatIds) { this.seatIds = seatIds; }

    public List<FoodItem> getFoods() { return foods; }
    public void setFoods(List<FoodItem> foods) { this.foods = foods; }

    public static class FoodItem {
        private Integer foodId;
        private Integer quantity;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
