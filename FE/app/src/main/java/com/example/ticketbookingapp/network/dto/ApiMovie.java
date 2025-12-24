package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;

public class ApiMovie {
    @SerializedName("filmId")
    private int filmId;

    private String title;
    private String genre;
    private String posterUrl;
    private float rating;

    public int getFilmId() { return filmId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getPosterUrl() { return posterUrl; }
    public float getRating() { return rating; }


}
