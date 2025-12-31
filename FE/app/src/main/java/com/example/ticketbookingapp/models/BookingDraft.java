package com.example.ticketbookingapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class BookingDraft implements Serializable {

    public int showtimeId;
    public int basePrice;

    public String filmTitle;
    public String cinemaName;
    public String roomName;
    public String dateText;
    public String timeText;

    public ArrayList<Integer> seatIds = new ArrayList<>();
    public int seatTotalPrice;

    public ArrayList<FoodDraftItem> foods = new ArrayList<>();
    public int foodTotalPrice;

    public BookingDraft() {}

    public int getGrandTotal() {
        return seatTotalPrice + foodTotalPrice;
    }
}
