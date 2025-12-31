package com.example.ticketbookingapp.network.dto;

import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {
    @SerializedName("filmId")
    public int filmId;

    @SerializedName("title")
    public String title;

    @SerializedName("genre")
    public String genre;

    @SerializedName("posterUrl")
    public String posterUrl;

    @SerializedName("rating")
    public Double rating;      // có thể 0.0

    @SerializedName("duration")
    public Integer duration;   // phút (có thể null)

    @SerializedName("ageRating")
    public String ageRating;

    @SerializedName("description")
    public String description;

    @SerializedName("director")
    public String director;

    @SerializedName("cast")
    public String cast;

    @SerializedName("release")
    public String release;     // "2021-12-17"

    @SerializedName("avgRating")
    public Double avgRating;   // null/number

    @SerializedName("reviewCount")
    public Integer reviewCount; // null/number

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
}
