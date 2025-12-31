package com.nhom9.movieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.FoodDto;

@Service
public interface FoodService {
    List<FoodDto> getAllFoods();
    

}
