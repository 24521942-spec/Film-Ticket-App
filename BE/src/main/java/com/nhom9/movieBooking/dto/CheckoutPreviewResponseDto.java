package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutPreviewResponseDto {
    private Integer userId;
    private Integer showtimeId;

    private List<SeatDto> seats;
    private List<FoodLineDto> foods;

    private BigDecimal seatTotal;
    private BigDecimal foodTotal;
    private BigDecimal totalPay;

    public static class FoodLineDto {
        private Integer foodId;
        private String foodName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal lineTotal;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }

        public String getFoodName() { return foodName; }
        public void setFoodName(String foodName) { this.foodName = foodName; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public BigDecimal getLineTotal() { return lineTotal; }
        public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Integer showtimeId) { this.showtimeId = showtimeId; }

    public List<SeatDto> getSeats() { return seats; }
    public void setSeats(List<SeatDto> seats) { this.seats = seats; }

    public List<FoodLineDto> getFoods() { return foods; }
    public void setFoods(List<FoodLineDto> foods) { this.foods = foods; }

    public BigDecimal getSeatTotal() { return seatTotal; }
    public void setSeatTotal(BigDecimal seatTotal) { this.seatTotal = seatTotal; }

    public BigDecimal getFoodTotal() { return foodTotal; }
    public void setFoodTotal(BigDecimal foodTotal) { this.foodTotal = foodTotal; }

    public BigDecimal getTotalPay() { return totalPay; }
    public void setTotalPay(BigDecimal totalPay) { this.totalPay = totalPay; }
}
