package com.nhom9.movieBooking.model;

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
@Table(name="CINEMA")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private Integer cinemaId;

    @Column(name = "cine_name", nullable = false, length = 100)
    private String cineName;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToMany(
        mappedBy = "cinema",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Room> rooms;

    

    public Cinema() {
    }

    public Cinema(String address, String cineName, int cinemaId, String phoneNumber, List<Room> rooms) {
        this.address = address;
        this.cineName = cineName;
        this.cinemaId = cinemaId;
        this.phoneNumber = phoneNumber;
        this.rooms = rooms;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCineName() {
        return cineName;
    }

    public void setCineName(String cineName) {
        this.cineName = cineName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    




    
}
