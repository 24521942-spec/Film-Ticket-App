package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.dto.BookingBriefDto;
import com.nhom9.movieBooking.model.Booking;
import com.nhom9.movieBooking.model.BookingSeat;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    @Query("""
        select new com.nhom9.movieBooking.dto.BookingBriefDto(
            b.bookingId,
            f.title,
            c.cineName,
            cast(st.startTime as string),
            b.statusBooking,
            f.posterUrl
        )
        from Booking b
        join b.showtime st
        join st.film f
        join st.room r
        join r.cinema c
        where b.user.userId = :userId
        order by st.startTime desc
    """)
    List<BookingBriefDto> findBriefByUserId(@Param("userId") Integer userId);
    //  List<BookingSeat> findByBookingBookingId(Integer bookingId);
}
