package com.nhom9.movieBooking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="FOOD")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Integer foodId;

   @Column(name = "food_name", nullable = false, length = 100)
    private String foodName;

    @Column(name = "food_type", length = 50)
    private String foodType;

    @Column(name = "size", length = 20)
    private String size;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "food_status", length = 20)
    private String foodStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingFood> bookingFoods;

    
    public Food() {}

    public Food(List<BookingFood> bookingFoods, LocalDateTime createdAt, Integer foodId, String foodName, String foodStatus, String foodType, String size, BigDecimal unitPrice, LocalDateTime updatedAt) {
        this.bookingFoods = bookingFoods;
        this.createdAt = createdAt;
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodStatus = foodStatus;
        this.foodType = foodType;
        this.size = size;
        this.unitPrice = unitPrice;
        this.updatedAt = updatedAt;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(String foodStatus) {
        this.foodStatus = foodStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<BookingFood> getBookingFoods() {
        return bookingFoods;
    }

    public void setBookingFoods(List<BookingFood> bookingFoods) {
        this.bookingFoods = bookingFoods;
    }

    
}
