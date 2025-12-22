package com.nhom9.movieBooking.model;

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
@Table(name="SEAT")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;



    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatHold> seatHolds;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingSeat> bookingSeats;

    @Column(name = "seat_code", nullable = false, length = 10)
    private String seatCode;

    @Column(name = "row_label", length = 10)
    private String rowLabel;

    @Column(name = "col_label")
    private Integer colLabel;

    @Column(name = "seat_type", length = 20)
    private String seatType;

    
    public Seat() {
    }

    public Seat(List<BookingSeat> bookingSeats, Integer colLabel, Room room, String rowLabel, String seatCode, List<SeatHold> seatHolds, Integer seatId, String seatType) {
        this.bookingSeats = bookingSeats;
        this.colLabel = colLabel;
        this.room = room;
        this.rowLabel = rowLabel;
        this.seatCode = seatCode;
        this.seatHolds = seatHolds;
        this.seatId = seatId;
        this.seatType = seatType;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public Integer getColLabel() {
        return colLabel;
    }

    public void setColLabel(Integer colLabel) {
        this.colLabel = colLabel;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    

    

    

    



}
