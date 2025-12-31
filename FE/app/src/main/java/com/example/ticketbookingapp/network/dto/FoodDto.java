package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;

public class FoodDto {
    @SerializedName("foodId")
    public Integer foodId;

    @SerializedName("foodName")
    public String foodName;

    @SerializedName("foodType")
    public String foodType;

    @SerializedName("unitPrice")
    public Double unitPrice;

    @SerializedName("quantity")
    public Integer quantity;

    public FoodDto(Integer foodId, String foodName, String foodType, Double unitPrice, Integer quantity) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodType = foodType;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
