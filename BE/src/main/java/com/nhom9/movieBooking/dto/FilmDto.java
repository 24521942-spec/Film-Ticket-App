package com.nhom9.movieBooking.dto;

public class FilmDto {
    private int filmId;
    private String title;
    private String genre;
    private int duration;
    private String ageRating;
    private String description;

    public FilmDto(String ageRating, int duration, int filmId, String genre, String title, String description) {
        this.ageRating = ageRating;
        this.duration = duration;
        this.filmId = filmId;
        this.genre = genre;
        this.title = title;
        this.description = description;
    }
    

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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







}
