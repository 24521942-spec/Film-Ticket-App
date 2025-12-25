package com.nhom9.movieBooking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingSeat> bookingSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_time_id", nullable = false)
    private ShowTime showtime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "total_pay", precision = 12, scale = 2)
    private BigDecimal totalPay;

    @Column(name = "status_booking", length = 50)
    private String statusBooking;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "subtotal_seat", nullable = false)
    private long subtotalSeat;

    @Column(name = "subtotal_food", nullable = false)
    private long subtotalFood;

    @Column(name = "total_amount", nullable = false)
    private long totalAmount;

    
    public Booking() {}

    

    public Booking(Integer bookingId, List<BookingSeat> bookingSeats, User user, ShowTime showtime,
            LocalDateTime createdAt, int totalTickets, BigDecimal totalPay, String statusBooking, String note,
            long subtotalSeat, long subtotalFood, long totalAmount) {
        this.bookingId = bookingId;
        this.bookingSeats = bookingSeats;
        this.user = user;
        this.showtime = showtime;
        this.createdAt = createdAt;
        this.totalTickets = totalTickets;
        this.totalPay = totalPay;
        this.statusBooking = statusBooking;
        this.note = note;
        this.subtotalSeat = subtotalSeat;
        this.subtotalFood = subtotalFood;
        this.totalAmount = totalAmount;
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



    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public List<BookingSeat> getBookingSeats() {
        return bookingSeats;
    }

    public void setBookingSeats(List<BookingSeat> bookingSeats) {
        this.bookingSeats = bookingSeats;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShowTime getShowtime() {
        return showtime;
    }

    public void setShowtime(ShowTime showtime) {
        this.showtime = showtime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public String getStatusBooking() {
        return statusBooking;
    }

    public void setStatusBooking(String statusBooking) {
        this.statusBooking = statusBooking;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
    


    


  





}
