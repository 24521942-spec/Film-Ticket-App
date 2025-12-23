package com.nhom9.movieBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom9.movieBooking.dto.FoodDto;
import com.nhom9.movieBooking.service.FoodService;


@RestController
@RequestMapping("/api/foods")
public class FoodControlller {
    private final FoodService foodService;

    public FoodControlller(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<FoodDto> getAllFoods() {
        return foodService.getAllFoods();
    }
    


}
