package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookingRequestDto {
    private int userId;
    private int showtimeId;
    private List<Integer> seats;
    private List<FoodDto> foodItems;
    private String paymentMethod;
    private BigDecimal totalAmount;

    public BookingRequestDto(List<FoodDto> foodItems, String paymentMethod, List<Integer> seats, int showtimeId, BigDecimal totalAmount, int userId) {
        this.foodItems = foodItems;
        this.paymentMethod = paymentMethod;
        this.seats = seats;
        this.showtimeId = showtimeId;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public List<FoodDto> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodDto> foodItems) {
        this.foodItems = foodItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }



    
}
