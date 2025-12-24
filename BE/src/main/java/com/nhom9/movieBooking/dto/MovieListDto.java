package com.nhom9.movieBooking.dto;

public class MovieListDto {
    private Integer filmId;
    private String title;
    private String genre;
    private String posterUrl;
    private float rating;

    public MovieListDto(Integer filmId, String genre, String posterUrl, float rating, String title) {
        this.filmId = filmId;
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.rating = rating;
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

    

}
