package com.nhom9.movieBooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom9.movieBooking.dto.FoodDto;
import com.nhom9.movieBooking.mapper.FoodMapper;
import com.nhom9.movieBooking.model.Food;
import com.nhom9.movieBooking.repository.FoodRepository;
import com.nhom9.movieBooking.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }



    @Override
    public List<FoodDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(FoodMapper::toFoodDto)
                .collect(Collectors.toList());
    }

    
}
