package com.example.ticketbookingapp.network.dto;

import java.util.ArrayList;
import java.util.List;

public class FakeBookingRequestDto {
    public int showtimeId;
    public int userId;
    public List<Integer> seatIds = new ArrayList<>();

    // nếu BE có nhận đồ ăn
    public List<FoodItem> foods = new ArrayList<>();

    public static class FoodItem {
        public int foodId;
        public int quantity;

        public FoodItem(int foodId, int quantity) {
            this.foodId = foodId;
            this.quantity = quantity;
        }
    }
}
