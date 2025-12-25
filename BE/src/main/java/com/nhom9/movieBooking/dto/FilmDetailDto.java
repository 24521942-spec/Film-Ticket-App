package com.nhom9.movieBooking.dto;

public class FilmDetailDto {
    private Integer filmId;
    private String title;
    private String genre;
    private String posterUrl;
    private float rating;

    private Integer duration;
    private String ageRating;
    private String description;

    private String director;
    private String cast;
    private String release; 


   

    public FilmDetailDto() {}

    public FilmDetailDto(String ageRating, String cast, String description, String director, Integer duration, Integer filmId, String genre, String posterUrl, float rating, String release, String title) {
        this.ageRating = ageRating;
        this.cast = cast;
        this.description = description;
        this.director = director;
        this.duration = duration;
        this.filmId = filmId;
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.release = release;
        this.title = title;
    }

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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

    
}
