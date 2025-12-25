package com.example.ticketbookingapp.models;
import java.io.Serializable;

public class BookingOrder implements Serializable {
    public String movieName = "";
    public String cinemaName = "";
    public String seats = "";
    public String foodSelected = "";
    public int totalAmount = 0;
}