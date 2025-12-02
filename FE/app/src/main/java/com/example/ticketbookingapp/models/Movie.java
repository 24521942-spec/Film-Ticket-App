package com.example.ticketbookingapp.models;

public class Movie {
    private String title;
    private String genre;
    private String posterUrl;
    private float rating;

    public Movie(String title, String genre, String posterUrl, float rating) {
        this.title = title;
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
