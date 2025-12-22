package com.nhom9.movieBooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.SeatHold;

@Repository
public interface SeatholdRepository extends  JpaRepository<SeatHold, Integer>{
    List<SeatHold> findByShowtimeShowTimeIdAndExpireAtAfter(Integer showtimeId, LocalDateTime now);
    boolean existsBySeatSeatIdAndShowtimeShowTimeIdAndExpireAtAfter(Integer seatId, Integer showtimeId, LocalDateTime now);
    List<SeatHold> findByUserUserIdAndShowtimeShowTimeId(Integer userId, Integer showtimeId);
    List<SeatHold> findByExpireAtBefore(LocalDateTime now);
    void deleteByShowtimeShowTimeId(Integer showtimeId);

    List<SeatHold> findByUserUserIdAndShowtimeShowTimeIdAndExpireAtAfter(Integer userId, Integer showtimeId, LocalDateTime now);
}
