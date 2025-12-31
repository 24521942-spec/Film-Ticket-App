package com.nhom9.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom9.movieBooking.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{
    List<Seat> findByRoomRoomId(Integer roomId);
}
