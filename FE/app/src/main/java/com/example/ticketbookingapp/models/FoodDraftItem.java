package com.example.ticketbookingapp.models;

import java.io.Serializable;

public class FoodDraftItem implements Serializable {
    public int foodId;
    public String name;
    public int unitPrice;
    public int quantity;

    public FoodDraftItem(int foodId, String name, int unitPrice, int quantity) {
        this.foodId = foodId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getTotal() {
        return unitPrice * quantity;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
