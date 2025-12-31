package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhom9.movieBooking.model.BookingSeat;

import jakarta.transaction.Transactional;

public interface  BookingseatRepository extends JpaRepository<BookingSeat, Integer> {
        boolean existsByShowtimeShowTimeIdAndSeatSeatIdAndBookingStatusBookingIn(
            Integer showtimeId, Integer seatId, List<String> lockedStatuses);

        List<BookingSeat> findByShowtimeShowTimeIdAndBookingStatusBookingIn(
            Integer showtimeId, List<String> statuses);
        @Transactional
        void deleteByBookingBookingId(Integer bookingId);

        @Query("""
                select bs
                from BookingSeat bs
                where bs.showtime.showTimeId = :showtimeId
                and bs.booking.statusBooking in :statuses
        """)
        List<BookingSeat> findByShowtimeAndBookingStatusIn(
                @Param("showtimeId") Integer showtimeId,
                @Param("statuses") List<String> statuses
        );

        List<BookingSeat> findByBookingBookingId(Integer bookingId);


}
