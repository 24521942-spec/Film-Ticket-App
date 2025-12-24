package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("""
        select r.film.filmId, avg(r.rating)
        from Review r
        group by r.film.filmId
    """)
    List<Object[]> avgRatingByFilm();

    @Query("select avg(r.rating) from Review r where r.film.filmId = :filmId")
    Double avgRatingByFilmId(@Param("filmId") Integer filmId);
}
