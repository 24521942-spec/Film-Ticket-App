package com.nhom9.movieBooking.dto;

public class FoodItemRes {
    public Integer foodId;
    public String name;
    public long unitPrice;
    public int quantity;
    public long lineTotal;
    
    public FoodItemRes(Integer foodId, String name, long unitPrice, int quantity, long lineTotal) {
        this.foodId = foodId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }
    public Integer getFoodId() {
        return foodId;
    }
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public long getLineTotal() {
        return lineTotal;
    }
    public void setLineTotal(long lineTotal) {
        this.lineTotal = lineTotal;
    }
    
}
