package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailDto {
    public Integer bookingId;
    public String status;

    public Integer showtimeId;
    public String cinemaName;
    public String roomName;
    public String showTime;

    public String filmTitle;
    public String posterUrl;

    public String qrUrl; // ✅ thêm dòng này

    public List<SeatLine> seats = new ArrayList<>();
    public List<FoodLine> foods = new ArrayList<>();

    public BigDecimal ticketTotal = BigDecimal.ZERO;
    public BigDecimal foodTotal = BigDecimal.ZERO;
    public BigDecimal totalPay = BigDecimal.ZERO;

    public static class SeatLine {
        public Integer seatId;
        public String seatCode;
        public String seatType;
        public BigDecimal price;
    }

    public static class FoodLine {
        public Integer foodId;
        public String foodName;
        public Integer quantity;
        public BigDecimal unitPrice;
        public BigDecimal lineTotal;
    }
}

