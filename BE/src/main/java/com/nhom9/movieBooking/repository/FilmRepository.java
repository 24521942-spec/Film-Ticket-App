package com.nhom9.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    
}
