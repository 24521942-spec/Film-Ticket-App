package com.nhom9.movieBooking.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookingResponseDto {
    private int bookingId;
    private int userId;
    private int showtimeId;

    private String cinemaName;   
    private String roomName;     
    private String showTime;     

    
    private String gateQrContent;
    private String gateQrBase64Png;

    private List<SeatDto> seats;


    private List<FoodLineDto> foods;
    private BigDecimal ticketUnitPrice; 
    private BigDecimal ticketTotal;     
    private BigDecimal foodTotal;       
    private BigDecimal totalPay;        

    private String status;
    private String paymentStatus;

    public BookingResponseDto() {}

    
    public BookingResponseDto(int bookingId, String paymentStatus, List<SeatDto> seats, int showtimeId, String status, int userId) {
        this.bookingId = bookingId;
        this.paymentStatus = paymentStatus;
        this.seats = seats;
        this.showtimeId = showtimeId;
        this.status = status;
        this.userId = userId;
    }

    
    public BookingResponseDto(
            int bookingId,
            int userId,
            int showtimeId,
            List<SeatDto> seats,
            List<FoodLineDto> foods,
            BigDecimal ticketUnitPrice,
            BigDecimal ticketTotal,
            BigDecimal foodTotal,
            BigDecimal totalPay,
            String status,
            String paymentStatus
    ) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seats = seats;
        this.foods = foods;
        this.ticketUnitPrice = ticketUnitPrice;
        this.ticketTotal = ticketTotal;
        this.foodTotal = foodTotal;
        this.totalPay = totalPay;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getShowtimeId() { return showtimeId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }

    public String getCinemaName() { return cinemaName; }
    public void setCinemaName(String cinemaName) { this.cinemaName = cinemaName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }

    public String getGateQrContent() { return gateQrContent; }
    public void setGateQrContent(String gateQrContent) { this.gateQrContent = gateQrContent; }

    public String getGateQrBase64Png() { return gateQrBase64Png; }
    public void setGateQrBase64Png(String gateQrBase64Png) { this.gateQrBase64Png = gateQrBase64Png; }

    public List<SeatDto> getSeats() { return seats; }
    public void setSeats(List<SeatDto> seats) { this.seats = seats; }

    public List<FoodLineDto> getFoods() { return foods; }
    public void setFoods(List<FoodLineDto> foods) { this.foods = foods; }

    public BigDecimal getTicketUnitPrice() { return ticketUnitPrice; }
    public void setTicketUnitPrice(BigDecimal ticketUnitPrice) { this.ticketUnitPrice = ticketUnitPrice; }

    public BigDecimal getTicketTotal() { return ticketTotal; }
    public void setTicketTotal(BigDecimal ticketTotal) { this.ticketTotal = ticketTotal; }

    public BigDecimal getFoodTotal() { return foodTotal; }
    public void setFoodTotal(BigDecimal foodTotal) { this.foodTotal = foodTotal; }

    public BigDecimal getTotalPay() { return totalPay; }
    public void setTotalPay(BigDecimal totalPay) { this.totalPay = totalPay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    
    public static class FoodLineDto {
        private Integer foodId;
        private String foodName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal lineTotal;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }

        public String getFoodName() { return foodName; }
        public void setFoodName(String foodName) { this.foodName = foodName; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public BigDecimal getLineTotal() { return lineTotal; }
        public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
    }
}
