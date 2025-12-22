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
@Table(name="SHOWTIME")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_time_id")
    private Integer showTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatHold> seatHolds;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingSeat> bookingSeats;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "base_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Column(name = "language_film", length = 50)
    private String languageFilm;

    @Column(name = "subtitle", length = 50)
    private String subtitle;

    @Column(name = "status_film", length = 20)
    private String statusFilm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
    public ShowTime() {}

    public ShowTime(BigDecimal basePrice, List<BookingSeat> bookingSeats, LocalDateTime createdAt, Film film, String languageFilm, Room room, List<SeatHold> seatHolds, Integer showTimeId, LocalDateTime startTime, String statusFilm, String subtitle, LocalDateTime updatedAt) {
        this.basePrice = basePrice;
        this.bookingSeats = bookingSeats;
        this.createdAt = createdAt;
        this.film = film;
        this.languageFilm = languageFilm;
        this.room = room;
        this.seatHolds = seatHolds;
        this.showTimeId = showTimeId;
        this.startTime = startTime;
        this.statusFilm = statusFilm;
        this.subtitle = subtitle;
        this.updatedAt = updatedAt;
    }

    public Integer getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Integer showTimeId) {
        this.showTimeId = showTimeId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getLanguageFilm() {
        return languageFilm;
    }

    public void setLanguageFilm(String languageFilm) {
        this.languageFilm = languageFilm;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getStatusFilm() {
        return statusFilm;
    }

    public void setStatusFilm(String statusFilm) {
        this.statusFilm = statusFilm;
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

    public List<SeatHold> getSeatHolds() {
        return seatHolds;
    }

    public void setSeatHolds(List<SeatHold> seatHolds) {
        this.seatHolds = seatHolds;
    }

    public List<BookingSeat> getBookingSeats() {
        return bookingSeats;
    }

    public void setBookingSeats(List<BookingSeat> bookingSeats) {
        this.bookingSeats = bookingSeats;
    }

  
    


}
