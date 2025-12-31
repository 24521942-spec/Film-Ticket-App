package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;

public class FoodDto {
    private int foodId;
    private String foodName;
    private String foodType;
    private BigDecimal unitPrice;

    private int quantity;

    public FoodDto() {}

    public FoodDto(int foodId, String foodName, String foodType, int quantity, BigDecimal unitPrice) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodType = foodType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }


    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}
