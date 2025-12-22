package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom9.movieBooking.model.BookingSeat;

public interface  BookingseatRepository extends JpaRepository<BookingSeat, Integer> {
    List<BookingSeat> findByShowtimeShowTimeIdAndBookingStatusBooking(Integer showtime, String statusBooking);
    boolean existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBooking(
        Integer showtimeId, Integer seatId, String statusBooking
    );
}
