package com.nhom9.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    
}
