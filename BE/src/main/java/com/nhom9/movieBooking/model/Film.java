package com.nhom9.movieBooking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="FILM")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer filmId;

    @OneToMany(
        mappedBy = "film",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ShowTime> showtimes;

    @OneToMany(
        mappedBy = "film",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Review> reviews;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "genre", length = 100)
    private String genre;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "age_rating", length = 20)
    private String ageRating;

    @Column(name = "description_film", columnDefinition = "TEXT")
    private String descriptionFilm;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "poster_url", length = 255)
    private String posterUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "director", length = 255)
    private String director;

    @Column(name = "cast_text", columnDefinition = "TEXT")
    private String castText;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Film() {
    }

    

    public Film(Integer filmId, List<ShowTime> showtimes, List<Review> reviews, String title, String genre,
            Integer duration, String ageRating, String descriptionFilm, LocalDate startDate, LocalDate endDate,
            String posterUrl, LocalDateTime createdAt, LocalDateTime updatedAt, String director, String castText,
            LocalDate releaseDate) {
        this.filmId = filmId;
        this.showtimes = showtimes;
        this.reviews = reviews;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.ageRating = ageRating;
        this.descriptionFilm = descriptionFilm;
        this.startDate = startDate;
        this.endDate = endDate;
        this.posterUrl = posterUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.director = director;
        this.castText = castText;
        this.releaseDate = releaseDate;
    }



    public String getDirector() {
        return director;
    }



    public void setDirector(String director) {
        this.director = director;
    }



    public String getCastText() {
        return castText;
    }



    public void setCastText(String castText) {
        this.castText = castText;
    }



    public LocalDate getReleaseDate() {
        return releaseDate;
    }



    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }



    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public List<ShowTime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowTime> showtimes) {
        this.showtimes = showtimes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    public String getDescriptionFilm() {
        return descriptionFilm;
    }

    public void setDescriptionFilm(String descriptionFilm) {
        this.descriptionFilm = descriptionFilm;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    
    
}
