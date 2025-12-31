package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;

public class FilmDto {
    @SerializedName("filmId")
    public Integer filmId;

    @SerializedName("title")
    public String title;

    // tùy BE của bạn: "genre" / "category" / "type"
    @SerializedName("genre")
    public String genre;

    // tùy BE của bạn: "posterUrl" / "poster" / "image"
    @SerializedName("posterUrl")
    public String posterUrl;

    // tùy BE của bạn: "rating" / "avgRating"
    @SerializedName("rating")
    public Float rating;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
