package com.example.ticketbookingapp.models;

public class Session {
    private String time;
    private String format; // IMAX, 2D, 3D
    private String language;
    private double priceAdult;
    private double priceChild;
    private double priceStudent;

    public Session(String time, String format, String language, double priceAdult, double priceChild, double priceStudent) {
        this.time = time;
        this.format = format;
        this.language = language;
        this.priceAdult = priceAdult;
        this.priceChild = priceChild;
        this.priceStudent = priceStudent;
    }
    // Getter cho các trường tương ứng với layout item_session_by_time_row.xml
    public String getTime() { return time; }
    public String getFormat() { return format; }
    public String getLanguage() { return language; }
    public double getPriceAdult() { return priceAdult; }
}