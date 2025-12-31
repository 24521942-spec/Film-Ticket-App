package com.nhom9.movieBooking.mapper;

import com.nhom9.movieBooking.dto.FoodDto;
import com.nhom9.movieBooking.model.Food;

public class FoodMapper {
    public static FoodDto toFoodDto(Food food) {
        FoodDto dto = new FoodDto();
        dto.setFoodId(food.getFoodId());
        dto.setFoodName(food.getFoodName());
        dto.setUnitPrice(food.getUnitPrice());

        return dto;
    }
}
