package com.nhom9.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    
}
