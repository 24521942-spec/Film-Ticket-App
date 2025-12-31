package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class BookingDetailDto implements Serializable {

    @SerializedName("bookingId") public int bookingId;
    @SerializedName("status") public String status;

    @SerializedName("cinemaName") public String cinemaName;
    @SerializedName("roomName") public String roomName;
    @SerializedName("showTime") public String showTime;

    @SerializedName("filmTitle") public String filmTitle;
    @SerializedName("posterUrl") public String posterUrl;

    @SerializedName("qrUrl") public String qrUrl;

    @SerializedName("seats") public List<SeatItem> seats;
    @SerializedName("foods") public List<FoodItem> foods;

    @SerializedName("totalPay") public Double totalPay;

    public static class SeatItem implements Serializable {
        @SerializedName("seatId") public int seatId;
        @SerializedName("seatCode") public String seatCode;
        @SerializedName("seatType") public String seatType;
        @SerializedName("price") public Double price;
    }

    public static class FoodItem implements Serializable {
        @SerializedName("foodId") public int foodId;
        @SerializedName("foodName") public String foodName;
        @SerializedName("quantity") public int quantity;
        @SerializedName("unitPrice") public Double unitPrice;
        @SerializedName("lineTotal") public Double lineTotal;
    }

}
