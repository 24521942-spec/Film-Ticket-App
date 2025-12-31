package com.nhom9.movieBooking.dto;

import java.util.List;


public class CheckoutConfirmRe {
    public Integer bookingId;
    public Integer userId;
    public Integer showtimeId;
    public String status;         
    public String paymentStatus;  

    public List<SeatDto> seats;   
    public List<FoodItemRes> foods;

    public long subtotalSeat;
    public long subtotalFood;
    public long totalAmount;
    public CheckoutConfirmRe(Integer bookingId, Integer userId, Integer showtimeId, String status, String paymentStatus,
            List<SeatDto> seats, List<FoodItemRes> foods, long subtotalSeat, long subtotalFood, long totalAmount) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.seats = seats;
        this.foods = foods;
        this.subtotalSeat = subtotalSeat;
        this.subtotalFood = subtotalFood;
        this.totalAmount = totalAmount;
    }
    public Integer getBookingId() {
        return bookingId;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getShowtimeId() {
        return showtimeId;
    }
    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public List<SeatDto> getSeats() {
        return seats;
    }
    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }
    public List<FoodItemRes> getFoods() {
        return foods;
    }
    public void setFoods(List<FoodItemRes> foods) {
        this.foods = foods;
    }
    public long getSubtotalSeat() {
        return subtotalSeat;
    }
    public void setSubtotalSeat(long subtotalSeat) {
        this.subtotalSeat = subtotalSeat;
    }
    public long getSubtotalFood() {
        return subtotalFood;
    }
    public void setSubtotalFood(long subtotalFood) {
        this.subtotalFood = subtotalFood;
    }
    public long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    
}
