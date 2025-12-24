package com.example.ticketbookingapp.models;

public class Session {
    private String time;
    private String format;
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

    public String getTime() { return time; }
    public String getFormat() { return format; }
    public String getLanguage() { return language; }
    public double getPriceAdult() { return priceAdult; }
    public double getPriceChild() { return priceChild; }
    public double getPriceStudent() { return priceStudent; }
}