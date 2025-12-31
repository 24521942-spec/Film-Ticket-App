package com.nhom9.movieBooking.dto;

public class MovieListDto {
    private Integer filmId;
    private String title;
    private String posterUrl;
    private Integer duration;   // phút
    private Float avgRating;
    private String genre;

    public MovieListDto() {}
    
    public MovieListDto(Integer filmId, String title, String posterUrl, Integer duration, Float avgRating,
            String genre) {
        this.filmId = filmId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.duration = duration;
        this.avgRating = avgRating;
        this.genre = genre;
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
    public String getPosterUrl() {
        return posterUrl;
    }
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Float getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
}
