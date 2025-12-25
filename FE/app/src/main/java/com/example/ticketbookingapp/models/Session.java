package com.example.ticketbookingapp.models;

public class Session {
    private String time;
    private String format;
    private String language;
    private double priceAdult;
    private double priceChild;
    private double priceStudent;
    private String priceVIP;
    private String cinemaName; // THÊM TRƯỜNG NÀY

    // Cập nhật Constructor để nhận thêm cinemaName
    public Session(String time, String format, String language, double priceAdult, double priceChild, double priceStudent, String priceVIP, String cinemaName) {
        this.time = time;
        this.format = format;
        this.language = language;
        this.priceAdult = priceAdult;
        this.priceChild = priceChild;
        this.priceStudent = priceStudent;
        this.priceVIP = priceVIP;
        this.cinemaName = cinemaName;
    }

    // Getters
    public String getTime() { return time; }
    public String getFormat() { return format; }
    public String getLanguage() { return language; }
    public double getPriceAdult() { return priceAdult; }
    public double getPriceChild() { return priceChild; }
    public double getPriceStudent() { return priceStudent; }
    public String getPriceVIP() { return priceVIP; }
    public String getCinemaName() { return cinemaName; } // THÊM GETTER
}