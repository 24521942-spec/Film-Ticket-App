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
    private Double avgRating;
    private Integer reviewCount;


   

    public FilmDetailDto() {}

    

    public FilmDetailDto(Integer filmId, String title, String genre, String posterUrl, float rating, Integer duration,
            String ageRating, String description, String director, String cast, String release, Double avgRating,
            Integer reviewCount) {
        this.filmId = filmId;
        this.title = title;
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.duration = duration;
        this.ageRating = ageRating;
        this.description = description;
        this.director = director;
        this.cast = cast;
        this.release = release;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
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
