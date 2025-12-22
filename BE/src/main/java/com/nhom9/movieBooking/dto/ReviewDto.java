package com.nhom9.movieBooking.dto;

import java.time.LocalDateTime;

public class ReviewDto {
    private int reviewId;
    private int rating;
    private String rvCommnet;
    private LocalDateTime CreatedAt;

    public ReviewDto(LocalDateTime CreatedAt, int rating, int reviewId, String rvCommnet) {
        this.CreatedAt = CreatedAt;
        this.rating = rating;
        this.reviewId = reviewId;
        this.rvCommnet = rvCommnet;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRvCommnet() {
        return rvCommnet;
    }

    public void setRvCommnet(String rvCommnet) {
        this.rvCommnet = rvCommnet;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime CreatedAt) {
        this.CreatedAt = CreatedAt;
    }


    
}
