package com.nhom9.movieBooking.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="booking_food")
public class BookingFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_food_id")
    private int bookingFoodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price_at_order", precision = 10, scale = 2)
    private BigDecimal unitPriceAtOrder;

    @Column(name = "line_total", precision = 12, scale = 2)
    private BigDecimal lineTotal;

    
    public BookingFood() {}

    public BookingFood(Booking booking, int bookingFoodId, Food food, BigDecimal lineTotal, int quantity, BigDecimal unitPriceAtOrder) {
        this.booking = booking;
        this.bookingFoodId = bookingFoodId;
        this.food = food;
        this.lineTotal = lineTotal;
        this.quantity = quantity;
        this.unitPriceAtOrder = unitPriceAtOrder;
    }

    public int getBookingFoodId() {
        return bookingFoodId;
    }

    public void setBookingFoodId(int bookingFoodId) {
        this.bookingFoodId = bookingFoodId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPriceAtOrder() {
        return unitPriceAtOrder;
    }

    public void setUnitPriceAtOrder(BigDecimal unitPriceAtOrder) {
        this.unitPriceAtOrder = unitPriceAtOrder;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    
   
    

}
