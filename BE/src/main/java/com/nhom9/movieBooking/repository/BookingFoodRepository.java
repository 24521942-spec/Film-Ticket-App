package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.BookingFood;

@Repository
public interface BookingFoodRepository extends JpaRepository<BookingFood, Integer>{
    void deleteByBookingBookingId(Integer bookingId);
    List<BookingFood> findByBookingBookingId(Integer bookingId);

}
